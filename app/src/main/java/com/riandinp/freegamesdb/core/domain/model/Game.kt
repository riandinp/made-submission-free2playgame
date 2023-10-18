package com.riandinp.freegamesdb.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Game(
    val id: Int,
    val title: String,
    val thumbnail: String,
    val shortDescription: String,
    var description: String? = null,
    val gameUrl: String,
    val genre: String,
    val platform: String,
    val publisher: String,
    val developer: String,
    val releaseDate: String,
    val freetogameProfileUrl: String,
    var screenshots: List<String> = emptyList(),
    var isFavorite: Boolean = false
) : Parcelable
