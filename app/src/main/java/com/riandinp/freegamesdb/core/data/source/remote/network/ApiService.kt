package com.riandinp.freegamesdb.core.data.source.remote.network

import com.riandinp.freegamesdb.core.data.source.remote.response.DetailGameResponse
import com.riandinp.freegamesdb.core.data.source.remote.response.GameResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("games")
    suspend fun getList(): List<GameResponse>

    @GET("game")
    suspend fun getDetailGames(@Query("id") id: Int): DetailGameResponse
}