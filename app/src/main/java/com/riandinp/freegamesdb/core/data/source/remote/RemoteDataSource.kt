package com.riandinp.freegamesdb.core.data.source.remote

import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.riandinp.freegamesdb.core.data.source.remote.network.ApiResponse
import com.riandinp.freegamesdb.core.data.source.remote.response.DetailGameResponse
import com.riandinp.freegamesdb.core.data.source.remote.response.GameResponse
import com.riandinp.freegamesdb.core.utlis.JsonHelper
import org.json.JSONException

class RemoteDataSource private constructor(private val jsonHelper: JsonHelper) {
    companion object {
        @Volatile
        private var instance: RemoteDataSource? = null

        fun getInstance(helper: JsonHelper): RemoteDataSource =
            instance ?: synchronized(this) {
                instance ?: RemoteDataSource(helper)
            }
    }

    fun getAllGames(): LiveData<ApiResponse<List<GameResponse>>> {
        val resultData = MutableLiveData<ApiResponse<List<GameResponse>>>()

        //get data from local json
        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            try {
                val dataArray = jsonHelper.loadData()
                if (dataArray.isNotEmpty()) {
                    resultData.value = ApiResponse.Success(dataArray)
                } else {
                    resultData.value = ApiResponse.Empty
                }
            } catch (e: JSONException){
                resultData.value = ApiResponse.Error(e.toString())
                Log.e("RemoteDataSource", e.toString())
            }
        }, 2000)

        return resultData
    }

    fun getDetailGames(id: Int): LiveData<ApiResponse<DetailGameResponse>> {
        val resultData = MutableLiveData<ApiResponse<DetailGameResponse>>()

        //get data from local json
        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            try {
                val dataArray = jsonHelper.loadDetailData(id)
                if (dataArray != null) {
                    resultData.value = ApiResponse.Success(dataArray)
                } else {
                    resultData.value = ApiResponse.Empty
                }
            } catch (e: JSONException){
                resultData.value = ApiResponse.Error(e.toString())
                Log.e("RemoteDataSource", e.toString())
            }
        }, 2000)

        return resultData
    }
}

