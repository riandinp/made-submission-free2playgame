package com.riandinp.freegamesdb.core.domain.usecase

import com.riandinp.freegamesdb.core.data.Resource
import com.riandinp.freegamesdb.core.domain.model.Game
import com.riandinp.freegamesdb.core.domain.repository.IGameRepository
import com.riandinp.freegamesdb.core.utils.ListGamesDummy
import com.riandinp.freegamesdb.core.utils.ListScreenshotsDummy.generateImages
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectIndexed
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.verifyNoMoreInteractions
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GameUseCaseTest {

    companion object {
        const val CATEGORY = "RPG"
        const val DESCRIPTION_EXAMPLE = "Example of full description"
    }

    private lateinit var gameUseCase: GameUseCase
    private lateinit var gameList: Flow<Resource<List<Game>>>
    private lateinit var gameListBasedOnCategory: Flow<List<Game>>
    private lateinit var gameDetail: Flow<Resource<Game>>
    private lateinit var gameFavorite: Flow<List<Game>>

    @Mock
    private lateinit var gameRepository: IGameRepository

    @Before
    fun setUp() {
        gameUseCase = GameInteractor(gameRepository)

        gameList = flow {
            emit(Resource.Loading())
            emit(Resource.Success(ListGamesDummy.generateGamesAsDomain()))
        }
        `when`(gameRepository.getAllGames()).thenReturn(gameList)

        gameListBasedOnCategory = flowOf(ListGamesDummy.generateGamesAsDomain(CATEGORY))
        `when`(gameRepository.getAllGamesBasedOnCategory(CATEGORY)).thenReturn(
            gameListBasedOnCategory
        )

        gameDetail = flow {
            emit(Resource.Loading())
            emit(
                Resource.Success(
                    ListGamesDummy.generateGamesAsDomain(
                        screenshots = generateImages(),
                        description = DESCRIPTION_EXAMPLE
                    ).first()
                )
            )
        }
        `when`(
            gameRepository.getDetailGames(
                ListGamesDummy.generateGamesAsDomain().first()
            )
        ).thenReturn(gameDetail)

        gameFavorite = flowOf(
            ListGamesDummy.generateGamesAsDomain(
                screenshots = generateImages(),
                description = DESCRIPTION_EXAMPLE,
                isFavorite = true
            )
        )
        `when`(gameRepository.getFavoriteGames()).thenReturn(
            gameFavorite
        )
    }

    @Test
    fun `get all list games`() = runTest {
        gameUseCase.getAllGames().collectIndexed { index, value ->
            if (index == 0) {
                Assert.assertEquals(true, value is Resource.Loading)
            } else {
                Assert.assertEquals(true, value is Resource.Success)
                Assert.assertNotNull("Data is null", value.data)
            }
        }

        verify(gameRepository).getAllGames()
        verifyNoMoreInteractions(gameRepository)
    }

    @Test
    fun `get detail game`() = runTest {
        val exampleData = ListGamesDummy.generateGamesAsDomain().first()

        gameUseCase.getDetailGame(exampleData).collectIndexed { index, game ->
            if (index == 0) {
                Assert.assertEquals("It should be Resource.Loading", true, game is Resource.Loading)
            } else {
                Assert.assertEquals("It should be Resource.Success", true, game is Resource.Success)

                // Check if data is not null
                Assert.assertNotNull("Data is null", game.data)

                // Check if description is not null
                Assert.assertNotNull("Description is null", game.data?.description)
            }
        }

        verify(gameRepository).getDetailGames(exampleData)
        verifyNoMoreInteractions(gameRepository)
    }

    @Test
    fun `get list game based on category`() = runTest {

        gameUseCase.getAllGamesBasedOnCategory(CATEGORY).collect { value ->
            if(value.isNotEmpty()) {
                // check if genre should be RPG
                value.forEach { game ->
                    Assert.assertEquals("Genre should be RPG", CATEGORY , game.genre)
                }
            } else {
                // check if it is empty
                Assert.assertEquals("List should be empty", listOf<Game>() , value)
            }
        }

        verify(gameRepository).getAllGamesBasedOnCategory(CATEGORY)
        verifyNoMoreInteractions(gameRepository)
    }

    @Test
    fun `get favorite games`() = runTest {
        gameUseCase.getFavoriteGames().collect { value ->
            if(value.isEmpty()) {
                // check if it is empty
                Assert.assertEquals("List should be empty", listOf<Game>() , value)
            } else {
                // check is favorite should be true
                value.forEach { game ->
                    Assert.assertTrue("is favorite should be true", game.isFavorite)
                }
            }
        }

        verify(gameRepository).getFavoriteGames()
        verifyNoMoreInteractions(gameRepository)
    }

    @Test
    fun `set favorite games to false`() = runTest {
        val game = ListGamesDummy.generateGamesAsDomain(
            screenshots = generateImages(),
            description = DESCRIPTION_EXAMPLE,
            isFavorite = true
        ).first()
        gameUseCase.setFavoriteGames(game, false)

        verify(gameRepository).setFavoriteGames(game, false)
        verifyNoMoreInteractions(gameRepository)
    }

}