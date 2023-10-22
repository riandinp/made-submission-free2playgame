package com.riandinp.freegamesdb.ui.home

import androidx.lifecycle.ViewModel
import com.riandinp.freegamesdb.core.domain.usecase.GameUseCase

class HomeViewModel(gameUseCase: GameUseCase) : ViewModel() {

    val games = gameUseCase.getAllGames()
}