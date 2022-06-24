package com.rosseti.domain.usecase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.rosseti.domain.SchedulerProvider
import com.rosseti.domain.entity.GameEntity
import com.rosseti.domain.repository.GameRepository
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class CreateGameUseCaseTest {
    private lateinit var createGameUseCase: CreateGameUseCase

    @MockK
    lateinit var schedulers: SchedulerProvider

    @MockK(relaxed = true)
    lateinit var gameRepository: GameRepository

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        createGameUseCase = CreateGameUseCase(schedulers, gameRepository)
    }

    @Test
    fun `Given valid credentials, when call api createGame, should return the game object`() {
        //Given
        val playerId = "1"
        val playerName = "Test"
        val adversaryId = "Test"
        val adversaryName = "Test"
        val score = "Test"
        val scoreAdversary = "Test"
        val game = GameEntity(id = playerId)

        every { schedulers.subscribeOn } returns Schedulers.io()
        every { schedulers.observeOn } returns Schedulers.io()

        every {
            gameRepository.createGame(
                playerId,
                adversaryId,
                playerName,
                adversaryName,
                score,
                scoreAdversary
            )
        } returns Single.just(game)

        //when
        val result = createGameUseCase(
            playerId,
            adversaryId,
            playerName,
            adversaryName,
            score,
            scoreAdversary
        ).test()

        //then
        result.assertNotTerminated().assertNoErrors()
        result.dispose()
    }
}