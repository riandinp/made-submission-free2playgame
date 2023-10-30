package com.riandinp.freegamesdb.core.utils

import com.riandinp.freegamesdb.core.data.source.local.entity.GameEntity
import com.riandinp.freegamesdb.core.data.source.remote.response.GameResponse
import com.riandinp.freegamesdb.core.domain.model.Game

object DataMapper {
    fun mapResponsesToEntities(input: List<GameResponse>): List<GameEntity> {
        val gameList = ArrayList<GameEntity>()
        input.map { response ->
            val game = GameEntity(
                id = response.id,
                title = response.title,
                thumbnail = response.thumbnail,
                shortDescription = response.shortDescription,
                gameUrl = response.gameUrl,
                genre = response.genre,
                platform = response.platform,
                publisher = response.publisher,
                developer = response.developer,
                releaseDate = response.releaseDate,
                freetogameProfileUrl = response.freetogameProfileUrl,
                isFavorite = false
            )
            gameList.add(game)
        }
        return gameList
    }

    fun mapEntitiesToDomain(input: List<GameEntity>): List<Game> =
        input.map { entity ->
            Game(
                id = entity.id,
                title = entity.title,
                thumbnail = entity.thumbnail,
                description = entity.description,
                shortDescription = entity.shortDescription,
                gameUrl = entity.gameUrl,
                genre = entity.genre,
                platform = entity.platform,
                publisher = entity.publisher,
                developer = entity.developer,
                releaseDate = entity.releaseDate,
                freetogameProfileUrl = entity.freetogameProfileUrl,
                screenshots = entity.screenshots,
                isFavorite = entity.isFavorite
            )
        }

    fun mapDomainToEntity(input: Game) = GameEntity(
        id = input.id,
        title = input.title,
        thumbnail = input.thumbnail,
        description = input.description,
        shortDescription = input.shortDescription,
        gameUrl = input.gameUrl,
        genre = input.genre,
        platform = input.platform,
        publisher = input.publisher,
        developer = input.developer,
        releaseDate = input.releaseDate,
        freetogameProfileUrl = input.freetogameProfileUrl,
        screenshots = input.screenshots,
        isFavorite = input.isFavorite
    )
}