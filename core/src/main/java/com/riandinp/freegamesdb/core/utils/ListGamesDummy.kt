package com.riandinp.freegamesdb.core.utils

import com.riandinp.freegamesdb.core.data.source.local.entity.GameEntity
import com.riandinp.freegamesdb.core.data.source.remote.response.DetailGameResponse
import com.riandinp.freegamesdb.core.data.source.remote.response.GameResponse
import com.riandinp.freegamesdb.core.domain.model.Game

object ListGamesDummy {

    fun generateGamesAsResponse(): List<GameResponse> {
        val gameList = mutableListOf<GameResponse>()
        for (i in 1..10) {
            gameList.add(
                GameResponse(
                    id = i,
                    title = "Game $i",
                    thumbnail = "https://picsum.photos/200/300",
                    shortDescription = "Example Short Description $i",
                    gameUrl = "https://example.com",
                    genre = "RPG",
                    platform = "PC (Windows)",
                    publisher = "Example Publisher",
                    developer = "Example Publisher",
                    releaseDate = "2019-01-08",
                    freetogameProfileUrl = "https://example.com",
                )
            )
        }
        return gameList
    }

    fun generateDetailGamesAsResponse(screenshots: List<DetailGameResponse.ScreenshotsItem> = emptyList()): List<DetailGameResponse> {
        val gameList = mutableListOf<DetailGameResponse>()
        for (i in 1..10) {
            gameList.add(
                DetailGameResponse(
                    id = i,
                    title = "Game $i",
                    thumbnail = "https://picsum.photos/200/300",
                    shortDescription = "Example Short Description $i",
                    description = "Example of full description",
                    gameUrl = "https://example.com",
                    genre = "RPG",
                    platform = "PC (Windows)",
                    publisher = "Example Publisher",
                    developer = "Example Publisher",
                    releaseDate = "2019-01-08",
                    freetogameProfileUrl = "https://example.com",
                    screenshots = screenshots
                )
            )
        }
        return gameList
    }

    fun generateGamesAsDomain(
        genre: String = "RPG",
        screenshots: List<String> = emptyList(),
        description: String? = null,
        isFavorite: Boolean = false
    ): List<Game> {
        val gameList = mutableListOf<Game>()
        for (i in 1..10) {
            gameList.add(
                Game(
                    id = i,
                    title = "Game $i",
                    thumbnail = "https://picsum.photos/200/300",
                    description =  description,
                    shortDescription = "Example Short Description $i",
                    gameUrl = "https://example.com",
                    genre = genre,
                    platform = "PC (Windows)",
                    publisher = "Example Publisher",
                    developer = "Example Publisher",
                    releaseDate = "2019-01-08",
                    freetogameProfileUrl = "https://example.com",
                    screenshots = screenshots,
                    isFavorite = isFavorite
                )
            )
        }
        return gameList
    }

    fun generateGamesAsEntity(
        genre: String = "RPG",
        screenshots: List<String> = emptyList(),
        description: String? = null,
        isFavorite: Boolean = false
    ): List<GameEntity> {
        val gameList = mutableListOf<GameEntity>()
        for (i in 1..10) {
            gameList.add(
                GameEntity(
                    id = i,
                    title = "Game $i",
                    thumbnail = "https://picsum.photos/200/300",
                    shortDescription = "Example Short Description $i",
                    description = description,
                    gameUrl = "https://example.com",
                    genre = genre,
                    platform = "PC (Windows)",
                    publisher = "Example Publisher",
                    developer = "Example Publisher",
                    releaseDate = "2019-01-08",
                    freetogameProfileUrl = "https://example.com",
                    screenshots = screenshots,
                    isFavorite = isFavorite
                )
            )
        }
        return gameList
    }
}