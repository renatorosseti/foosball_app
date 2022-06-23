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

    @MockK
    lateinit var updatePlayerUseCase: UpdatePlayerUseCase

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()


    @Before
    fun setup() {
        MockKAnnotations.init(this)
        viewModel = GameDetailsViewModel(
            updatePlayerUseCase,
            createPlayerUseCase,
            updateGameUseCase,
            createGameUseCase
        )
    }

    @Test
    fun `Given player detail, when call the api, should update the player detail object`() {
        //given
        val playerId = "1"
        val playerName = "Test"
        val adversaryId = "Test"
        val adversaryName = "Test"
        val score = "Test"
        val scoreAdversary = "Test"
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
            PlayerDetailsViewState.ShowContent(viewModel.playerDetail, game),
            viewModel.response.value
        )
    }

    @Test
    fun `Given error emission, when load create player with error, should update error`() {
        //Given
        val playerId = "1"
        val playerName = "Test"
        val adversaryId = "Test"
        val adversaryName = "Test"
        val score = "Test"
        val scoreAdversary = "Test"
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
        } returns Single.error(
            error
        )

        //when
        viewModel.createGame(
            playerId,
            adversaryId,
            playerName,
            adversaryName,
            score,
            scoreAdversary
        )

        //should
        Assert.assertEquals(
            viewModel.response.value,
            PlayerDetailsViewState.ShowNetworkError(error)
        )
    }
}