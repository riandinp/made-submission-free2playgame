package com.riandinp.freegamesdb.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.riandinp.freegamesdb.core.domain.usecase.GameUseCase

class HomeViewModel(private val gameUseCase: GameUseCase) : ViewModel() {

    fun getListGames() = gameUseCase.getAllGames().asLiveData()
}