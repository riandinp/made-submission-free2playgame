package com.riandinp.freegamesdb.core.data.source.remote.response

import com.google.gson.annotations.SerializedName


data class DetailGameResponse(
    @field:SerializedName("short_description")
    val shortDescription: String,

    @field:SerializedName("thumbnail")
    val thumbnail: String,

    @field:SerializedName("freetogame_profile_url")
    val freetogameProfileUrl: String,

    @field:SerializedName("description")
    val description: String,

    @field:SerializedName("title")
    val title: String,

    @field:SerializedName("platform")
    val platform: String,

    @field:SerializedName("screenshots")
    val screenshots: List<ScreenshotsItem> = emptyList(),

    @field:SerializedName("game_url")
    val gameUrl: String,

    @field:SerializedName("release_date")
    val releaseDate: String,

    @field:SerializedName("genre")
    val genre: String,

    @field:SerializedName("publisher")
    val publisher: String,

    @field:SerializedName("developer")
    val developer: String,

    @field:SerializedName("id")
    val id: Int,
) {
    data class ScreenshotsItem(
        @field:SerializedName("image")
        val image: String,

        @field:SerializedName("id")
        val id: Int
    )
}


