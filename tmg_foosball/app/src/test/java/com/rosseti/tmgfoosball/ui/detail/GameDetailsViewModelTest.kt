package com.rosseti.tmgfoosball.ui.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.rosseti.domain.entity.PlayerEntity
import com.rosseti.domain.usecase.CreatePlayerUseCase
import com.rosseti.domain.usecase.GetScoreDetailsUseCase
import com.rosseti.domain.usecase.UpdatePlayerUseCase
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
    lateinit var getScoreDetailsUseCase: GetScoreDetailsUseCase

    @MockK
    lateinit var updatePlayerUseCase: UpdatePlayerUseCase

    @MockK
    lateinit var createPlayerUseCase: CreatePlayerUseCase

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private val scoreId = 100

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        viewModel = GameDetailsViewModel(getScoreDetailsUseCase, updatePlayerUseCase, createPlayerUseCase)
    }

    @Test
    fun `Given score detail, when call the api, should update the score detail object`() {

        //given
        val domainData = PlayerEntity(id = scoreId, name = "test", matches = "1", scores = "1")
        viewModel.playerDetail.value = domainData

        val domainDataUpdated = PlayerEntity(id = scoreId, name = "test", matches = "10", scores = "10")
        every{ updatePlayerUseCase(scoreId,"test", matches = "10", scores = "10") } returns Single.just(domainDataUpdated)

        //when
        viewModel.requestNewGame("test", games = "10", scores = "10")

        //to check the one value for testing
        Assert.assertNotNull(viewModel.playerDetail)

        Assert.assertEquals(PlayerDetailsViewState.ShowContent(domainDataUpdated), viewModel.response.value)
    }

    @Test
    fun `Given score detail null, when call the api, should create the score detail object`() {

        //given
        val domainData = null
        viewModel.playerDetail.value = domainData

        val domainDataUpdated = PlayerEntity(id = scoreId, name = "test", matches = "10", scores = "10")
        every{ createPlayerUseCase("test", matches = "10", scores = "10") } returns Single.just(domainDataUpdated)

        //when
        viewModel.requestNewGame("test", games = "10", scores = "10")

        //to check the one value for testing
        Assert.assertNotNull(viewModel.playerDetail)

        Assert.assertEquals(domainDataUpdated, viewModel.playerDetail.value)
        Assert.assertEquals(PlayerDetailsViewState.ShowContent(domainDataUpdated), viewModel.response.value)
    }

    @Test
    @Throws(Exception::class)
    fun `Given error emission, when load repo score details with error, should update error`() {

        //Given
        val error = RuntimeException("Unknown error")

        every{ createPlayerUseCase("test", matches = "10", scores = "10") } returns Single.error(error)

        //when
        viewModel.requestNewGame("test", games = "10", scores = "10")

        //should
        Assert.assertEquals(viewModel.response.value, PlayerDetailsViewState.ShowNetworkError(error))
    }
}