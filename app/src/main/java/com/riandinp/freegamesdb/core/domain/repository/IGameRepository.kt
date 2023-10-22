package com.riandinp.freegamesdb.core.domain.repository

import com.riandinp.freegamesdb.core.data.Resource
import com.riandinp.freegamesdb.core.domain.model.Game
import kotlinx.coroutines.flow.Flow

interface IGameRepository {
    fun getAllGames(): Flow<Resource<List<Game>>>

    fun getFavoriteGames(): Flow<List<Game>>

    fun getAllGamesBasedOnCategory(category: String): Flow<List<Game>>

    fun getDetailGames(gameData: Game): Flow<Resource<Game>>

    fun setFavoriteGames(game: Game, state: Boolean)
}