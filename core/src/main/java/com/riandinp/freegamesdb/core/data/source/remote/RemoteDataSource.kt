package com.riandinp.freegamesdb.core.data.source.remote

import android.util.Log
import com.riandinp.freegamesdb.core.data.source.remote.network.ApiResponse
import com.riandinp.freegamesdb.core.data.source.remote.network.ApiService
import com.riandinp.freegamesdb.core.data.source.remote.response.DetailGameResponse
import com.riandinp.freegamesdb.core.data.source.remote.response.GameResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RemoteDataSource(
    private val apiService: ApiService,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {

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
        }.flowOn(ioDispatcher)
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
        }.flowOn(ioDispatcher)
    }
}

