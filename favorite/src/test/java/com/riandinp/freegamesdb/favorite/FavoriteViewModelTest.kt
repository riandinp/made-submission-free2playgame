package com.riandinp.freegamesdb.favorite

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.riandinp.freegamesdb.core.domain.model.Game
import com.riandinp.freegamesdb.core.domain.usecase.GameUseCase
import com.riandinp.freegamesdb.core.utils.ListGamesDummy
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class FavoriteViewModelTest {
    private lateinit var favoriteViewModel: FavoriteViewModel

    @Mock
    private lateinit var gamesUseCase: GameUseCase

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var gameList: Flow<List<Game>>

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()


    @Before
    fun setUp() {
        favoriteViewModel = FavoriteViewModel(gamesUseCase)
    }


    @Test
    fun `get list games`() = runTest {
        gameList = flowOf(ListGamesDummy.generateGamesAsDomain(isFavorite = true))
        Mockito.`when`(gamesUseCase.getFavoriteGames()).thenReturn(gameList)

        favoriteViewModel.getAllFavoriteGames().observeForever {
            it.forEach { game ->
                Assert.assertTrue("is Favorite should be true", game.isFavorite)
            }
        }
        Mockito.verify(gamesUseCase).getFavoriteGames()
    }
}