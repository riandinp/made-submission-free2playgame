package com.riandinp.freegamesdb.core.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "game")
data class GameEntity(
    @PrimaryKey
    @ColumnInfo(name = "gameId")
    val id: Int,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "thumbnail")
    val thumbnail: String,

    @ColumnInfo(name = "short_description")
    val shortDescription: String,

    @ColumnInfo(name = "description")
    var description: String? = null,

    @ColumnInfo(name = "game_url")
    val gameUrl: String,

    @ColumnInfo(name = "genre")
    val genre: String,

    @ColumnInfo(name = "platform")
    val platform: String,

    @ColumnInfo(name = "publisher")
    val publisher: String,

    @ColumnInfo(name = "developer")
    val developer: String,

    @ColumnInfo(name = "release_date")
    val releaseDate: String,

    @ColumnInfo(name = "freetogame_profile_url")
    val freetogameProfileUrl: String,

    @ColumnInfo(name = "screenshots")
    var screenshots: List<String> = emptyList(),

    @ColumnInfo(name = "isFavorite")
    var isFavorite: Boolean = false
)
