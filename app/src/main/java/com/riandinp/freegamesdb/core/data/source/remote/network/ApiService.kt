package com.riandinp.freegamesdb.core.data.source.remote.network

import com.riandinp.freegamesdb.core.data.source.remote.response.DetailGameResponse
import com.riandinp.freegamesdb.core.data.source.remote.response.GameResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("games")
    fun getList(): Call<List<GameResponse>>

    @GET("game")
    fun getDetailGames(@Query("id") id: Int): Call<DetailGameResponse>
}