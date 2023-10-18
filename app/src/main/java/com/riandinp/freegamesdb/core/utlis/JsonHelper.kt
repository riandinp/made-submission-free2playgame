package com.riandinp.freegamesdb.core.utlis

import android.content.Context
import androidx.annotation.RawRes
import com.riandinp.freegamesdb.R
import com.riandinp.freegamesdb.core.data.source.remote.response.DetailGameResponse
import com.riandinp.freegamesdb.core.data.source.remote.response.GameResponse
import org.json.JSONObject
import java.io.IOException

class JsonHelper(private val context: Context) {

    private fun parsingFileToString(@RawRes rawRes: Int): String? {
        val jsonString: String
        try {
            jsonString = context.resources.openRawResource(rawRes).bufferedReader()
                .use { it.readText() }
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            return null
        }
        return jsonString
    }

    fun loadData(): List<GameResponse> {
        val list = ArrayList<GameResponse>()
        val responseObject = JSONObject(parsingFileToString(R.raw.games).toString())
        val listArray = responseObject.getJSONArray("games")
        for (i in 0 until listArray.length()) {
            val game = listArray.getJSONObject(i)

            val id = game.getInt("id")
            val title = game.getString("title")
            val thumbnail = game.getString("thumbnail")
            val shortDescription = game.getString("short_description")
            val gameUrl = game.getString("game_url")
            val genre = game.getString("genre")
            val platform = game.getString("platform")
            val publisher = game.getString("publisher")
            val developer = game.getString("developer")
            val releaseDate = game.getString("release_date")
            val freetogameProfileUrl = game.getString("freetogame_profile_url")

            val gameResponse = GameResponse(
                id = id,
                title = title,
                thumbnail = thumbnail,
                shortDescription = shortDescription,
                gameUrl = gameUrl,
                genre = genre,
                platform = platform,
                publisher = publisher,
                developer = developer,
                releaseDate = releaseDate,
                freetogameProfileUrl = freetogameProfileUrl
            )
            list.add(gameResponse)
        }
        return list
    }

    fun loadDetailData(id: Int): DetailGameResponse {
        val game = JSONObject(parsingFileToString(R.raw.detailgames).toString())
        val title = game.getString("title")
        val thumbnail = game.getString("thumbnail")
        val shortDescription = game.getString("short_description")
        val gameUrl = game.getString("game_url")
        val genre = game.getString("genre")
        val platform = game.getString("platform")
        val publisher = game.getString("publisher")
        val developer = game.getString("developer")
        val releaseDate = game.getString("release_date")
        val listScreenshotsObj = game.getJSONArray("screenshots")
        val freetogameProfileUrl = game.getString("freetogame_profile_url")
        val description = game.getString("description")

        val listScreenshots = mutableListOf<DetailGameResponse.ScreenshotsItem>()

        for (i in 0 until listScreenshotsObj.length()) {
            val screenshot = listScreenshotsObj.getJSONObject(i)
            val idScreenshot = screenshot.getInt("id")
            val image = screenshot.getString("image")
            listScreenshots.add(DetailGameResponse.ScreenshotsItem(
                id = idScreenshot,
                image = image
            ))
        }

        return DetailGameResponse(
            id = id,
            title = title,
            thumbnail = thumbnail,
            description = description,
            shortDescription = shortDescription,
            gameUrl = gameUrl,
            genre = genre,
            platform = platform,
            publisher = publisher,
            developer = developer,
            releaseDate = releaseDate,
            freetogameProfileUrl = freetogameProfileUrl,
            screenshots = listScreenshots
        )
    }
}