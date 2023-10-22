package com.riandinp.freegamesdb.core.data.source.local

import com.riandinp.freegamesdb.core.data.source.local.entity.GameEntity
import com.riandinp.freegamesdb.core.data.source.local.room.GameDao
import kotlinx.coroutines.flow.Flow

class LocalDataSource(private val gameDao: GameDao) {

    fun getAllGames(): Flow<List<GameEntity>> = gameDao.getAllGames()

    fun getDetailGame(id: Int): Flow<GameEntity> = gameDao.getDetailGame(id)

    fun getFavoriteGames(): Flow<List<GameEntity>> = gameDao.getFavoriteGames()

    fun getAllGamesBasedOnCategory(category: String): Flow<List<GameEntity>> = gameDao.getAllGamesBasedOnCategory("%$category%")

    suspend fun insertGames(gameList: List<GameEntity>) = gameDao.insertGames(gameList)

    fun updateDetailGames(game: GameEntity, description: String, screenshots: List<String>) {
        game.description = description
        game.screenshots = screenshots
        gameDao.updateGames(game)
    }

    fun setFavoriteGames(game: GameEntity, newState: Boolean) {
        game.isFavorite = newState
        gameDao.updateGames(game)
    }
}