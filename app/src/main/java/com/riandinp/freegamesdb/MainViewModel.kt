package com.riandinp.freegamesdb

import androidx.lifecycle.ViewModel
import com.riandinp.freegamesdb.core.domain.usecase.GameUseCase

class MainViewModel(gameUseCase: GameUseCase) : ViewModel() {

    val games = gameUseCase.getAllGames()
}