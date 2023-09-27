package com.riandinp.freegamesdb.core.di

import android.content.Context
import com.riandinp.freegamesdb.core.data.GameRepository
import com.riandinp.freegamesdb.core.data.source.local.LocalDataSource
import com.riandinp.freegamesdb.core.data.source.local.room.GameDatabase
import com.riandinp.freegamesdb.core.data.source.remote.RemoteDataSource
import com.riandinp.freegamesdb.core.domain.repository.IGameRepository
import com.riandinp.freegamesdb.core.domain.usecase.GameInteractor
import com.riandinp.freegamesdb.core.domain.usecase.GameUseCase
import com.riandinp.freegamesdb.core.utlis.AppExecutors
import com.riandinp.freegamesdb.core.utlis.JsonHelper

object Injection {
    private fun provideRepository(context: Context): IGameRepository {
        val database = GameDatabase.getInstance(context)

        val remoteDataSource = RemoteDataSource.getInstance(JsonHelper(context))
        val localDataSource = LocalDataSource.getInstance(database.gameDao())
        val appExecutors = AppExecutors()

        return GameRepository.getInstance(remoteDataSource, localDataSource, appExecutors)
    }

    fun provideTourismUseCase(context: Context): GameUseCase {
        val repository = provideRepository(context)
        return GameInteractor(repository)
    }
}