package com.riandinp.freegamesdb.core.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.riandinp.freegamesdb.core.di.Injection
import com.riandinp.freegamesdb.core.domain.usecase.GameUseCase
import com.riandinp.freegamesdb.ui.category.CategoryViewModel
import com.riandinp.freegamesdb.ui.detail.DetailViewModel
import com.riandinp.freegamesdb.ui.favorite.FavoriteViewModel
import com.riandinp.freegamesdb.ui.home.HomeViewModel

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
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                HomeViewModel(gameUseCase) as T
            }
            modelClass.isAssignableFrom(CategoryViewModel::class.java) -> {
                CategoryViewModel(gameUseCase) as T
            }
            modelClass.isAssignableFrom(FavoriteViewModel::class.java) -> {
                FavoriteViewModel(gameUseCase) as T
            }
            modelClass.isAssignableFrom(DetailViewModel::class.java) -> {
                DetailViewModel(gameUseCase) as T
            }
            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
        }
}