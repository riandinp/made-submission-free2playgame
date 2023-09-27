package com.riandinp.freegamesdb.core.domain.usecase

import androidx.lifecycle.LiveData
import com.riandinp.freegamesdb.core.data.Resource
import com.riandinp.freegamesdb.core.domain.model.Game
import com.riandinp.freegamesdb.core.domain.repository.IGameRepository

class GameInteractor(private val gameRepository: IGameRepository) : GameUseCase {
    override fun getAllGames(): LiveData<Resource<List<Game>>> = gameRepository.getAllGames()

    override fun getFavoriteGames(): LiveData<List<Game>> = gameRepository.getFavoriteGames()

    override fun setFavoriteGames(game: Game, state: Boolean) =
        gameRepository.setFavoriteGames(game, state)
}