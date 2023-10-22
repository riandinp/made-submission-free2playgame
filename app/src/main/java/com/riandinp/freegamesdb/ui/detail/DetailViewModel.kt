package com.riandinp.freegamesdb.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.riandinp.freegamesdb.core.domain.model.Game
import com.riandinp.freegamesdb.core.domain.usecase.GameUseCase

class DetailViewModel(private val gameUseCase: GameUseCase): ViewModel() {

    fun getDetailGame(gameData: Game) = gameUseCase.getDetailGame(gameData).asLiveData()
    fun setFavoriteGame(gameData: Game, newStatus: Boolean) = gameUseCase.setFavoriteGames(gameData, newStatus)
}