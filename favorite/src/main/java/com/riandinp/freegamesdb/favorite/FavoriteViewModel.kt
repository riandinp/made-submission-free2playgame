package com.riandinp.freegamesdb.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.riandinp.freegamesdb.core.domain.usecase.GameUseCase

class FavoriteViewModel(private val gameUseCase: GameUseCase): ViewModel() {
    fun getAllFavoriteGames() = gameUseCase.getFavoriteGames().asLiveData()
}