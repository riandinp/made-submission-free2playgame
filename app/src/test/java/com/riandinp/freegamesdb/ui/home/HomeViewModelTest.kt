package com.riandinp.freegamesdb.ui.home

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
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class HomeViewModelTest {

    private lateinit var homeViewModel: HomeViewModel

    @Mock
    private lateinit var gamesUseCase: GameUseCase

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var observer: Observer<Resource<List<Game>>>

    private lateinit var gameList: Flow<Resource<List<Game>>>

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()


    @Before
    fun setUp() {
        homeViewModel = HomeViewModel(gamesUseCase)
    }


    @Test
    fun `get list games`() = runTest {
        gameList = flow {
            emit(Resource.Loading())
            emit(Resource.Success(ListGamesDummy.generateGamesAsDomain()))
        }
        Mockito.`when`(gamesUseCase.getAllGames()).thenReturn(gameList)

        homeViewModel.getListGames().observeForever(observer)
        verify(gamesUseCase).getAllGames()
    }
}