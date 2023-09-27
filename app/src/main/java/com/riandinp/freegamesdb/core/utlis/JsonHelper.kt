package com.riandinp.freegamesdb.core.utlis

import android.content.Context
import com.riandinp.freegamesdb.R
import com.riandinp.freegamesdb.core.data.source.remote.response.GameResponse
import org.json.JSONObject
import java.io.IOException

class JsonHelper(private val context: Context) {

    private fun parsingFileToString(): String? {
        val jsonString: String
        try {
            jsonString = context.resources.openRawResource(R.raw.games).bufferedReader()
                .use { it.readText() }
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            return null
        }
        return jsonString
    }

    fun loadData(): List<GameResponse> {
        val list = ArrayList<GameResponse>()
        val responseObject = JSONObject(parsingFileToString().toString())
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
}