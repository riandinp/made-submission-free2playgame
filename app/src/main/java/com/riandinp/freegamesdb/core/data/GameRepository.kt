package com.riandinp.freegamesdb.core.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.riandinp.freegamesdb.core.data.source.local.LocalDataSource
import com.riandinp.freegamesdb.core.data.source.remote.RemoteDataSource
import com.riandinp.freegamesdb.core.data.source.remote.network.ApiResponse
import com.riandinp.freegamesdb.core.data.source.remote.response.DetailGameResponse
import com.riandinp.freegamesdb.core.data.source.remote.response.GameResponse
import com.riandinp.freegamesdb.core.domain.model.Game
import com.riandinp.freegamesdb.core.domain.repository.IGameRepository
import com.riandinp.freegamesdb.core.utlis.AppExecutors
import com.riandinp.freegamesdb.core.utlis.DataMapper

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

    override fun getAllGames(): LiveData<Resource<List<Game>>> =
        object : NetworkBoundResource<List<Game>, List<GameResponse>>(appExecutors) {
            override fun loadFromDB(): LiveData<List<Game>> {
                return Transformations.map(localDataSource.getAllGames()) {
                    DataMapper.mapEntitiesToDomain(it)
                }
            }

            override fun shouldFetch(data: List<Game>?): Boolean =
                data.isNullOrEmpty()

            override fun createCall(): LiveData<ApiResponse<List<GameResponse>>> =
                remoteDataSource.getAllGames()

            override fun saveCallResult(data: List<GameResponse>) {
                val gameList = DataMapper.mapResponsesToEntities(data)
                localDataSource.insertGames(gameList)
            }
        }.asLiveData()

    override fun getFavoriteGames(): LiveData<List<Game>> {
        return Transformations.map(localDataSource.getFavoriteGames()) {
            DataMapper.mapEntitiesToDomain(it)
        }
    }

    override fun getDetailGames(id: Int, gameData: Game): LiveData<Resource<Game>> =
        object : NetworkBoundResource<Game, DetailGameResponse>(appExecutors) {
            override fun loadFromDB(): LiveData<Game> {
                return Transformations.map(localDataSource.getDetailGame(id)) { entity ->
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
                        screenshots = entity.screenshots
                    )
                }
            }

            override fun createCall(): LiveData<ApiResponse<DetailGameResponse>> =
                remoteDataSource.getDetailGames(id)

            override fun saveCallResult(data: DetailGameResponse) {
                val listScreenshots = mutableListOf<String>()
                val gameEntity = DataMapper.mapDomainToEntity(gameData)
                data.screenshots.forEach { screenshotsItem ->
                    listScreenshots.add(screenshotsItem.image)
                }
                appExecutors.diskIO().execute { localDataSource.updateDetailGames(gameEntity, data.description, listScreenshots) }
            }

            override fun shouldFetch(data: Game?): Boolean =
                data?.description.isNullOrEmpty() || data?.screenshots.isNullOrEmpty()

        }.asLiveData()

    override fun setFavoriteGames(game: Game, state: Boolean) {
        val gameEntity = DataMapper.mapDomainToEntity(game)
        appExecutors.diskIO().execute { localDataSource.setFavoriteGames(gameEntity, state) }
    }
}