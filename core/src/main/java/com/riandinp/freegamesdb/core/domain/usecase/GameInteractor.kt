package com.riandinp.freegamesdb.core.domain.usecase

import com.riandinp.freegamesdb.core.data.Resource
import com.riandinp.freegamesdb.core.domain.model.Game
import com.riandinp.freegamesdb.core.domain.repository.IGameRepository
import kotlinx.coroutines.flow.Flow

class GameInteractor(private val gameRepository: IGameRepository) : GameUseCase {
    override fun getAllGames(): Flow<Resource<List<Game>>> = gameRepository.getAllGames()

    override fun getFavoriteGames(): Flow<List<Game>> = gameRepository.getFavoriteGames()
    override fun getAllGamesBasedOnCategory(category: String): Flow<List<Game>> =
        gameRepository.getAllGamesBasedOnCategory(category)

    override fun setFavoriteGames(game: Game, state: Boolean) =
        gameRepository.setFavoriteGames(game, state)

    override fun getDetailGame(gameData: Game): Flow<Resource<Game>> =
        gameRepository.getDetailGames(gameData)
}