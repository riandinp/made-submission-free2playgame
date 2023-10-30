package com.riandinp.freegamesdb.ui.category

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.riandinp.freegamesdb.core.domain.model.Game
import com.riandinp.freegamesdb.core.domain.usecase.GameUseCase
import com.riandinp.freegamesdb.core.utils.ListGamesDummy
import com.riandinp.freegamesdb.utils.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class CategoryViewModelTest {

    companion object {
        const val CATEGORY = "RPG"
    }

    private lateinit var categoryViewModel: CategoryViewModel

    @Mock
    private lateinit var gamesUseCase: GameUseCase

    @Mock
    private lateinit var observer: Observer<List<Game>>

    private lateinit var gameListBasedOnCategory: Flow<List<Game>>

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        categoryViewModel = CategoryViewModel(gamesUseCase)
    }

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun `get list games based on category`() = runTest {
        gameListBasedOnCategory = flowOf(ListGamesDummy.generateGamesAsDomain(CATEGORY))
        Mockito.`when`(gamesUseCase.getAllGamesBasedOnCategory(CATEGORY)).thenReturn(gameListBasedOnCategory)

        categoryViewModel.getListGames(CATEGORY).observeForever(observer)
        Mockito.verify(gamesUseCase).getAllGamesBasedOnCategory(CATEGORY)
    }
}