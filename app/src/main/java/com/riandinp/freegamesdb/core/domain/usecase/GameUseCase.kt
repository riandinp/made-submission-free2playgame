package com.riandinp.freegamesdb.core.domain.usecase

import androidx.lifecycle.LiveData
import com.riandinp.freegamesdb.core.data.Resource
import com.riandinp.freegamesdb.core.domain.model.Game

interface GameUseCase {
    fun getAllGames(): LiveData<Resource<List<Game>>>
    fun getFavoriteGames(): LiveData<List<Game>>
    fun setFavoriteGames(game: Game, state: Boolean)
}