package com.riandinp.freegamesdb.ui.favorite

import androidx.lifecycle.ViewModel
import com.riandinp.freegamesdb.core.domain.usecase.GameUseCase

class FavoriteViewModel(gameUseCase: GameUseCase): ViewModel() {
    val favoriteGames = gameUseCase.getFavoriteGames()
}