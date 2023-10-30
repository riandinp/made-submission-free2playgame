package com.riandinp.freegamesdb.core.di

import androidx.room.Room
import com.riandinp.freegamesdb.core.BuildConfig
import com.riandinp.freegamesdb.core.data.GameRepository
import com.riandinp.freegamesdb.core.data.source.local.LocalDataSource
import com.riandinp.freegamesdb.core.data.source.local.room.GameDatabase
import com.riandinp.freegamesdb.core.data.source.remote.RemoteDataSource
import com.riandinp.freegamesdb.core.data.source.remote.network.ApiService
import com.riandinp.freegamesdb.core.domain.repository.IGameRepository
import com.riandinp.freegamesdb.core.utils.AppExecutors
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val databaseModule = module {
    factory { get<GameDatabase>().gameDao() }
    single {
        val passphrase: ByteArray = SQLiteDatabase.getBytes("dicoding-made".toCharArray())
        val factory = SupportFactory(passphrase)
        Room.databaseBuilder(
            androidContext(),
            GameDatabase::class.java, "games.db"
        ).fallbackToDestructiveMigration()
            .openHelperFactory(factory)
            .build()
    }
}

val networkModule = module {
    single {
        val loggingInterceptor =
            if(BuildConfig.DEBUG) HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            else HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)

        val hostname = "freetogame.com"
        val certificatePinner = CertificatePinner.Builder()
            .add(hostname, "sha256/aibC8oPHcGrFgJi2WmuIncc4TkOr+XPZfupJR+2yZ9g=")
            .build()

        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .certificatePinner(certificatePinner)
            .build()
    }
    single {
        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
        retrofit.create(ApiService::class.java)
    }
}

val repositoryModule = module {
    single { LocalDataSource(get()) }
    single { RemoteDataSource(get()) }
    factory { AppExecutors() }
    single<IGameRepository> {
        GameRepository(
            get(),
            get(),
            get()
        )
    }
}