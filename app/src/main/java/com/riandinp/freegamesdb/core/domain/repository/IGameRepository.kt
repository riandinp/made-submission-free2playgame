package com.riandinp.freegamesdb.core.domain.repository

import androidx.lifecycle.LiveData
import com.riandinp.freegamesdb.core.data.Resource
import com.riandinp.freegamesdb.core.domain.model.Game

interface IGameRepository {
    fun getAllGames(): LiveData<Resource<List<Game>>>

    fun getFavoriteGames(): LiveData<List<Game>>

    fun getDetailGames(gameData: Game): LiveData<Resource<Game>>

    fun setFavoriteGames(game: Game, state: Boolean)
}