package com.riandinp.freegamesdb.di

import com.riandinp.freegamesdb.core.domain.usecase.GameInteractor
import com.riandinp.freegamesdb.core.domain.usecase.GameUseCase
import com.riandinp.freegamesdb.ui.category.CategoryViewModel
import com.riandinp.freegamesdb.ui.detail.DetailViewModel
import com.riandinp.freegamesdb.ui.favorite.FavoriteViewModel
import com.riandinp.freegamesdb.ui.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val useCaseModule = module {
    factory<GameUseCase> { GameInteractor(get()) }
}

val viewModelModule = module {
    viewModel { HomeViewModel(get()) }
    viewModel { CategoryViewModel(get()) }
    viewModel { FavoriteViewModel(get()) }
    viewModel { DetailViewModel(get()) }
}