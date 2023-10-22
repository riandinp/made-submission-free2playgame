package com.riandinp.freegamesdb.ui.category

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.riandinp.freegamesdb.core.domain.usecase.GameUseCase

class CategoryViewModel(private val gameUseCase: GameUseCase) : ViewModel() {
    fun getListGames(category: String) = gameUseCase.getAllGamesBasedOnCategory(category).asLiveData()
}