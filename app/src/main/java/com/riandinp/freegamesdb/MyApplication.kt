package com.riandinp.freegamesdb

import android.app.Application
import com.riandinp.freegamesdb.core.di.databaseModule
import com.riandinp.freegamesdb.core.di.networkModule
import com.riandinp.freegamesdb.core.di.repositoryModule
import com.riandinp.freegamesdb.di.useCaseModule
import com.riandinp.freegamesdb.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MyApplication : Application(){
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@MyApplication)
            modules(
                listOf(
                    databaseModule,
                    networkModule,
                    repositoryModule,
                    useCaseModule,
                    viewModelModule
                )
            )
        }
    }
}