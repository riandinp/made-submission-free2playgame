package com.riandinp.freegamesdb.core.data

import com.riandinp.freegamesdb.core.data.source.local.LocalDataSource
import com.riandinp.freegamesdb.core.data.source.local.entity.GameEntity
import com.riandinp.freegamesdb.core.data.source.remote.RemoteDataSource
import com.riandinp.freegamesdb.core.data.source.remote.network.ApiResponse
import com.riandinp.freegamesdb.core.data.source.remote.response.DetailGameResponse
import com.riandinp.freegamesdb.core.data.source.remote.response.GameResponse
import com.riandinp.freegamesdb.core.domain.repository.IGameRepository
import com.riandinp.freegamesdb.core.utils.AppExecutors
import com.riandinp.freegamesdb.core.utils.ListGamesDummy
import com.riandinp.freegamesdb.core.utils.ListScreenshotsDummy.generateImages
import com.riandinp.freegamesdb.core.utils.ListScreenshotsDummy.generateImagesAsResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectIndexed
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.atLeast
import org.mockito.Mockito.atLeastOnce
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class GameRepositoryTest {

    companion object {
        const val GAME_ID = 1
        const val CATEGORY = "RPG"
        const val DESCRIPTION_EXAMPLE = "Example of full description"
    }

    @Mock
    private lateinit var remoteDataSource: RemoteDataSource

    @Mock
    private lateinit var localDataSource: LocalDataSource

    @Mock
    private lateinit var appExecutors: AppExecutors

    private lateinit var gameRepository: IGameRepository

    private lateinit var gameEntities: Flow<List<GameEntity>>
    private lateinit var gameEntitiesBasedOnCategory: Flow<List<GameEntity>>
    private lateinit var favoriteGameEntities: Flow<List<GameEntity>>
    private lateinit var gameEntity: Flow<GameEntity>
    private lateinit var gameResponse: Flow<ApiResponse<List<GameResponse>>>
    private lateinit var gameDetailResponse: Flow<ApiResponse<DetailGameResponse>>

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        gameRepository = GameRepository(remoteDataSource, localDataSource, appExecutors)

        gameEntities = flow {
            emit(ListGamesDummy.generateGamesAsEntity())
        }
        `when`(localDataSource.getAllGames()).thenReturn(gameEntities)

        gameEntitiesBasedOnCategory = flowOf(ListGamesDummy.generateGamesAsEntity(CATEGORY))
        `when`(localDataSource.getAllGamesBasedOnCategory(CATEGORY)).thenReturn(
            gameEntitiesBasedOnCategory
        )

        favoriteGameEntities = flowOf(
            ListGamesDummy.generateGamesAsEntity(
                description = DESCRIPTION_EXAMPLE,
                isFavorite = true
            )
        )
        `when`(localDataSource.getFavoriteGames()).thenReturn(
            favoriteGameEntities
        )

        gameEntity = flowOf(
            ListGamesDummy.generateGamesAsEntity(
                description = DESCRIPTION_EXAMPLE,
                screenshots = generateImages()
            ).first()
        )
        `when`(localDataSource.getDetailGame(GAME_ID)).thenReturn(
            gameEntity
        )

        gameResponse = flow {
            emit(ApiResponse.Success(ListGamesDummy.generateGamesAsResponse()))
        }
        `when`(remoteDataSource.getAllGames()).thenReturn(
            gameResponse
        )

        gameDetailResponse = flow {
            emit(
                ApiResponse.Success(
                    ListGamesDummy.generateDetailGamesAsResponse(
                        generateImagesAsResponse()
                    ).first()
                )
            )
        }
        `when`(remoteDataSource.getDetailGames(GAME_ID)).thenReturn(
            gameDetailResponse
        )
    }


    @Test
    fun `get list games`() = runTest {
        gameRepository.getAllGames().collectIndexed { index, value ->
            if (index == 0) {
                Assert.assertEquals(true, value is Resource.Loading)
            } else {
                Assert.assertEquals(true, value is Resource.Success)
                Assert.assertNotNull(value)
            }
        }
        verify(localDataSource, atLeast(2)).getAllGames()
    }

    @Test
    fun `get list games based on category`() = runTest {
        gameRepository.getAllGamesBasedOnCategory(CATEGORY).collect { value ->
            Assert.assertNotNull(value)
            value.forEach { game ->
                Assert.assertEquals("Genre should be RPG", CATEGORY, game.genre)
            }
        }
        verify(localDataSource, atLeastOnce()).getAllGamesBasedOnCategory(CATEGORY)
    }

    @Test
    fun `get detail game`() = runTest {
        val exampleData = ListGamesDummy.generateGamesAsDomain().first()
        gameRepository.getDetailGames(exampleData).collectIndexed { index, game ->
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
        verify(localDataSource, atLeast(2)).getDetailGame(GAME_ID)
    }

    @Test
    fun `get list favorite games`() = runTest {
        gameRepository.getFavoriteGames().collect { value ->
            Assert.assertNotNull(value)
            value.forEach { game ->
                Assert.assertTrue(game.isFavorite)
            }
        }
        verify(localDataSource, atLeastOnce()).getFavoriteGames()
    }
}