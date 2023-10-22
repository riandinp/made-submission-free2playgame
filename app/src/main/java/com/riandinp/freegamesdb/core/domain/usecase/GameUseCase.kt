package com.riandinp.freegamesdb.core.domain.usecase

import com.riandinp.freegamesdb.core.data.Resource
import com.riandinp.freegamesdb.core.domain.model.Game
import kotlinx.coroutines.flow.Flow

interface GameUseCase {
    fun getAllGames(): Flow<Resource<List<Game>>>
    fun getFavoriteGames(): Flow<List<Game>>
    fun getAllGamesBasedOnCategory(category: String): Flow<List<Game>>
    fun setFavoriteGames(game: Game, state: Boolean)
    fun getDetailGame(gameData: Game): Flow<Resource<Game>>
}