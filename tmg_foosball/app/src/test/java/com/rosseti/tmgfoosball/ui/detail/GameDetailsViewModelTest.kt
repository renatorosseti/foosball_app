package com.rosseti.tmgfoosball.ui.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.rosseti.domain.entity.GameEntity
import com.rosseti.domain.entity.PlayerEntity
import com.rosseti.domain.usecase.*
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.reactivex.Single
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class GameDetailsViewModelTest {
    private lateinit var viewModel: GameDetailsViewModel

    @MockK
    lateinit var createPlayerUseCase: CreatePlayerUseCase

    @MockK
    lateinit var updateGameUseCase: UpdateGameUseCase

    @MockK
    lateinit var createGameUseCase: CreateGameUseCase

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()


    @Before
    fun setup() {
        MockKAnnotations.init(this)
        viewModel = GameDetailsViewModel(
            createPlayerUseCase,
            updateGameUseCase,
            createGameUseCase
        )
    }

    private val gameId = "1"
    private val playerId = "1"
    private val playerName = "Test"
    private val adversaryId = "Test"
    private val adversaryName = "Test"
    private val score = "Test"
    private val scoreAdversary = "Test"

    @Test
    fun `Given valid credentials, when call api createGame, should return the game object`() {
        //given
        val game = GameEntity(id = playerId)
        val domainData = PlayerEntity(id = playerId, name = playerName, games = listOf(game))
        viewModel.playerDetail = domainData
        every {
            createGameUseCase(
                playerId,
                adversaryId,
                playerName,
                adversaryName,
                score,
                scoreAdversary
            )
        } returns Single.just(game)

        //when
        viewModel.createGame(
            playerId,
            adversaryId,
            playerName,
            adversaryName,
            score,
            scoreAdversary
        )

        //to check the one value for testing
        Assert.assertNotNull(viewModel.playerDetail)
        Assert.assertEquals(
            GameDetailsViewState.ShowContent(viewModel.playerDetail, game),
            viewModel.response.value
        )
    }

    @Test
    fun `Given error emission, when create game with error, should return error`() {
        //Given
        val error = RuntimeException("Unknown error")

        every {
            createGameUseCase(
                playerId,
                adversaryId,
                playerName,
                adversaryName,
                score,
                scoreAdversary
            )
        } returns Single.error(error)

        //when
        viewModel.createGame(
            playerId,
            adversaryId,
            playerName,
            adversaryName,
            score,
            scoreAdversary
        )

        //then
        Assert.assertEquals(
            viewModel.response.value,
            GameDetailsViewState.ShowNetworkError(error)
        )
    }

    @Test
    fun `Given valid credentials, when call api createPlayer, should return the player object`() {
        //given
        val game = GameEntity(id = gameId)
        val player = PlayerEntity(id = playerId, name = playerName, games = listOf(game))

        every {
            createPlayerUseCase(playerName)
        } returns Single.just(player)

        every {
            createGameUseCase(
                playerId,
                adversaryId,
                playerName,
                adversaryName,
                score,
                scoreAdversary
            )
        } returns Single.just(game)

        //when
        viewModel.createPlayer(
            playerName,
            adversaryId,
            adversaryName,
            score,
            scoreAdversary
        )

        //to check the one value for testing
        Assert.assertNotNull(viewModel.playerDetail)
        Assert.assertEquals(
            GameDetailsViewState.ShowContent(player, viewModel.gameDetail),
            viewModel.response.value
        )
    }

    @Test
    fun `Given error emission, when create player with error, should return error`() {
        //Given
        val error = RuntimeException("Unknown error")

        every {
            createPlayerUseCase(playerName)
        } returns Single.error(error)

        //when
        viewModel.createPlayer(
            playerName,
            adversaryId,
            adversaryName,
            score,
            scoreAdversary
        )

        //then
        Assert.assertEquals(
            viewModel.response.value,
            GameDetailsViewState.ShowNetworkError(error)
        )
    }

    @Test
    fun `Given valid credentials, when call api update, should return the game object`() {
        //given
        val game = GameEntity(id = gameId)
        val player = PlayerEntity(id = playerId, name = playerName, games = listOf(game))
        viewModel.playerDetail = player
        every {
            updateGameUseCase(
                gameId,
                playerId,
                adversaryId,
                playerName,
                adversaryName,
                score,
                scoreAdversary
            )
        } returns Single.just(game)

        //when
        viewModel.updateGame(
            gameId,
            playerId,
            adversaryId,
            playerName,
            adversaryName,
            score,
            scoreAdversary
        )

        //to check the one value for testing
        Assert.assertNotNull(viewModel.playerDetail)
        Assert.assertEquals(
            GameDetailsViewState.ShowContent(viewModel.playerDetail, game),
            viewModel.response.value
        )
    }

    @Test
    fun `Given error emission, when update game with error, should return error`() {
        //Given
        val error = RuntimeException("Unknown error")

        every {
            updateGameUseCase(
                gameId,
                playerId,
                adversaryId,
                playerName,
                adversaryName,
                score,
                scoreAdversary
            )
        } returns Single.error(error)

        //when
        viewModel.updateGame(
            gameId,
            playerId,
            adversaryId,
            playerName,
            adversaryName,
            score,
            scoreAdversary
        )

        //then
        Assert.assertEquals(
            viewModel.response.value,
            GameDetailsViewState.ShowNetworkError(error)
        )
    }
}