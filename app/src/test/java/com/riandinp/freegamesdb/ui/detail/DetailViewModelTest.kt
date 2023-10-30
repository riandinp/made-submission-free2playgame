package com.riandinp.freegamesdb.ui.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.riandinp.freegamesdb.core.data.Resource
import com.riandinp.freegamesdb.core.domain.model.Game
import com.riandinp.freegamesdb.core.domain.usecase.GameUseCase
import com.riandinp.freegamesdb.core.utils.ListGamesDummy
import com.riandinp.freegamesdb.utils.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.atLeastOnce
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class DetailViewModelTest {

    companion object {
        private const val DESCRIPTION = "Example Description"
    }

    private lateinit var detailViewModel: DetailViewModel

    @Mock
    private lateinit var gamesUseCase: GameUseCase

    @Mock
    private lateinit var observer: Observer<Resource<Game>>

    private lateinit var game: Flow<Resource<Game>>

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        detailViewModel = DetailViewModel(gamesUseCase)
    }

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun `get detail games`() = runTest {
        game = flow {
            emit(Resource.Loading())
            emit(Resource.Success(ListGamesDummy.generateGamesAsDomain(description = DESCRIPTION).first()))
        }
        val exampleData = ListGamesDummy.generateGamesAsDomain().first()
        Mockito.`when`(gamesUseCase.getDetailGame(exampleData)).thenReturn(game)

        detailViewModel.getDetailGame(exampleData).observeForever(observer)

        verify(gamesUseCase, atLeastOnce()).getDetailGame(exampleData)
    }

    @Test
    fun `set favorite game`() = runTest {
        game = flow {
            emit(Resource.Loading())
            emit(Resource.Success(ListGamesDummy.generateGamesAsDomain(description = DESCRIPTION).first()))
        }
        val exampleData = ListGamesDummy.generateGamesAsDomain().first()
        Mockito.`when`(gamesUseCase.getDetailGame(exampleData)).thenReturn(game)

        detailViewModel.setFavoriteGame(exampleData, true)

        verify(gamesUseCase, atLeastOnce()).setFavoriteGames(exampleData, true)
    }
}