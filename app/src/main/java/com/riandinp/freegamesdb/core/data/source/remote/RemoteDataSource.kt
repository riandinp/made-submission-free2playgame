package com.riandinp.freegamesdb.core.data.source.remote

import android.util.Log
import com.riandinp.freegamesdb.core.data.source.remote.network.ApiResponse
import com.riandinp.freegamesdb.core.data.source.remote.network.ApiService
import com.riandinp.freegamesdb.core.data.source.remote.response.DetailGameResponse
import com.riandinp.freegamesdb.core.data.source.remote.response.GameResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RemoteDataSource private constructor(private val apiService: ApiService) {
    companion object {
        @Volatile
        private var instance: RemoteDataSource? = null

        fun getInstance(apiService: ApiService): RemoteDataSource =
            instance ?: synchronized(this) {
                instance ?: RemoteDataSource(apiService)
            }
    }

    fun getAllGames(): Flow<ApiResponse<List<GameResponse>>> {
        return flow {
            try {
                val responseData = apiService.getList()
                if (responseData.isNotEmpty()) emit(ApiResponse.Success(responseData))
                else emit(ApiResponse.Empty)
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    fun getDetailGames(id: Int): Flow<ApiResponse<DetailGameResponse>> {
        return flow {
            try {
                val responseData = apiService.getDetailGames(id)
                emit(ApiResponse.Success(responseData))
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }
}

