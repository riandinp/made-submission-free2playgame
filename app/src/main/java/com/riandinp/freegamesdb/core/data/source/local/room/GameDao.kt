package com.riandinp.freegamesdb.core.data.source.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.riandinp.freegamesdb.core.data.source.local.entity.GameEntity

@Dao
interface GameDao {

    @Query("SELECT * FROM game")
    fun getAllGames(): LiveData<List<GameEntity>>

    @Query("SELECT * FROM game where isFavorite = 1")
    fun getFavoriteGames(): LiveData<List<GameEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertGames(game: List<GameEntity>)

    @Update
    fun updateFavoriteGames(tourism: GameEntity)
}