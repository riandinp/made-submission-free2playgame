package com.riandinp.freegamesdb.core.data.source.local

import androidx.lifecycle.LiveData
import com.riandinp.freegamesdb.core.data.source.local.entity.GameEntity
import com.riandinp.freegamesdb.core.data.source.local.room.GameDao

class LocalDataSource private constructor(private val gameDao: GameDao) {

    companion object {
        private var instance: LocalDataSource? = null

        fun getInstance(gameDao: GameDao): LocalDataSource =
            instance ?: synchronized(this) {
                instance ?: LocalDataSource(gameDao)
            }
    }

    fun getAllGames(): LiveData<List<GameEntity>> = gameDao.getAllGames()

    fun getFavoriteGames(): LiveData<List<GameEntity>> = gameDao.getFavoriteGames()

    fun insertGames(gameList: List<GameEntity>) = gameDao.insertGames(gameList)

    fun setFavoriteGames(game: GameEntity, newState: Boolean) {
        game.isFavorite = newState
        gameDao.updateFavoriteGames(game)
    }
}