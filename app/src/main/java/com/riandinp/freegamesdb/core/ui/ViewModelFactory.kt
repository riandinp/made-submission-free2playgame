package com.riandinp.freegamesdb.core.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.riandinp.freegamesdb.MainViewModel
import com.riandinp.freegamesdb.core.data.GameRepository
import com.riandinp.freegamesdb.core.di.Injection
import com.riandinp.freegamesdb.core.domain.usecase.GameUseCase

class ViewModelFactory private constructor(private val gameUseCase: GameUseCase) :
    ViewModelProvider.NewInstanceFactory() {

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null

        fun getInstance(context: Context): ViewModelFactory =
            instance
                ?: synchronized(this) {
                    instance
                        ?: ViewModelFactory(
                            Injection.provideTourismUseCase(context)
                        )
                }
    }

    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        @Suppress("UNCHECKED_CAST")
        when {
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                MainViewModel(gameUseCase) as T
            }
            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
        }
}