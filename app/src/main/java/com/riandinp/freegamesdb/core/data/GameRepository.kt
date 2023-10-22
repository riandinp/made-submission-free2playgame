package com.riandinp.freegamesdb.core.data

import com.riandinp.freegamesdb.core.data.source.local.LocalDataSource
import com.riandinp.freegamesdb.core.data.source.remote.RemoteDataSource
import com.riandinp.freegamesdb.core.data.source.remote.network.ApiResponse
import com.riandinp.freegamesdb.core.data.source.remote.response.DetailGameResponse
import com.riandinp.freegamesdb.core.data.source.remote.response.GameResponse
import com.riandinp.freegamesdb.core.domain.model.Game
import com.riandinp.freegamesdb.core.domain.repository.IGameRepository
import com.riandinp.freegamesdb.core.utlis.AppExecutors
import com.riandinp.freegamesdb.core.utlis.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GameRepository private constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) : IGameRepository {
    companion object {
        @Volatile
        private var instance: GameRepository? = null

        fun getInstance(
            remoteData: RemoteDataSource,
            localData: LocalDataSource,
            appExecutors: AppExecutors
        ): GameRepository =
            instance ?: synchronized(this) {
                instance ?: GameRepository(remoteData, localData, appExecutors)
            }
    }

    override fun getAllGames(): Flow<Resource<List<Game>>> =
        object : NetworkBoundResource<List<Game>, List<GameResponse>>() {
            override fun loadFromDB(): Flow<List<Game>> {
                return localDataSource.getAllGames().map { DataMapper.mapEntitiesToDomain(it) }
            }

            override fun shouldFetch(data: List<Game>?): Boolean =
                data.isNullOrEmpty()

            override suspend fun createCall(): Flow<ApiResponse<List<GameResponse>>> =
                remoteDataSource.getAllGames()

            override suspend fun saveCallResult(data: List<GameResponse>) {
                val gameList = DataMapper.mapResponsesToEntities(data)
                localDataSource.insertGames(gameList)
            }
        }.asFlow()

    override fun getFavoriteGames(): Flow<List<Game>> {
        return localDataSource.getFavoriteGames().map { DataMapper.mapEntitiesToDomain(it) }
    }

    override fun getAllGamesBasedOnCategory(category: String): Flow<List<Game>> {
        return localDataSource.getAllGamesBasedOnCategory(category).map { DataMapper.mapEntitiesToDomain(it) }
    }

    override fun getDetailGames(gameData: Game): Flow<Resource<Game>> =
        object : NetworkBoundResource<Game, DetailGameResponse>() {
            override fun loadFromDB(): Flow<Game> {
                return localDataSource.getDetailGame(gameData.id).map { entity ->
                    Game(
                        id = entity.id,
                        title = entity.title,
                        thumbnail = entity.thumbnail,
                        shortDescription = entity.shortDescription,
                        gameUrl = entity.gameUrl,
                        genre = entity.genre,
                        platform = entity.platform,
                        publisher = entity.publisher,
                        developer = entity.developer,
                        releaseDate = entity.releaseDate,
                        freetogameProfileUrl = entity.freetogameProfileUrl,
                        description = entity.description,
                        screenshots = entity.screenshots,
                        isFavorite = entity.isFavorite
                    )
                }
            }

            override suspend fun createCall(): Flow<ApiResponse<DetailGameResponse>> =
                remoteDataSource.getDetailGames(gameData.id)

            override suspend fun saveCallResult(data: DetailGameResponse) {
                val listScreenshots = mutableListOf<String>()
                val gameEntity = DataMapper.mapDomainToEntity(gameData)
                data.screenshots.forEach { screenshotsItem ->
                    listScreenshots.add(screenshotsItem.image)
                }
                appExecutors.diskIO().execute { localDataSource.updateDetailGames(gameEntity, data.description, listScreenshots) }
            }

            override fun shouldFetch(data: Game?): Boolean =
                data?.description.isNullOrEmpty() || data?.screenshots.isNullOrEmpty()

        }.asFlow()

    override fun setFavoriteGames(game: Game, state: Boolean) {
        val gameEntity = DataMapper.mapDomainToEntity(game)
        appExecutors.diskIO().execute { localDataSource.setFavoriteGames(gameEntity, state) }
    }
}