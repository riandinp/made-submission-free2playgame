package com.riandinp.freegamesdb.detail

import androidx.lifecycle.ViewModel
import com.riandinp.freegamesdb.core.domain.model.Game
import com.riandinp.freegamesdb.core.domain.usecase.GameUseCase

class DetailViewModel(private val gameUseCase: GameUseCase): ViewModel() {

    fun getDetailGame(id: Int, gameData: Game) = gameUseCase.getDetailGame(id, gameData)
}