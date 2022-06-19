package com.rosseti.tmgfoosball.ui.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.rosseti.domain.entity.GamerEntity
import com.rosseti.domain.usecase.CreateGamerUseCase
import com.rosseti.domain.usecase.GetScoreDetailsUseCase
import com.rosseti.domain.usecase.UpdateGamerUseCase
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.reactivex.Single
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class GamerDetailsViewModelTest {
    private lateinit var viewModel: GamerDetailsViewModel

    @MockK
    lateinit var getScoreDetailsUseCase: GetScoreDetailsUseCase

    @MockK
    lateinit var updateGamerUseCase: UpdateGamerUseCase

    @MockK
    lateinit var createGamerUseCase: CreateGamerUseCase

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private val scoreId = 100

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        viewModel = GamerDetailsViewModel(getScoreDetailsUseCase, updateGamerUseCase, createGamerUseCase)
    }

    @Test
    fun `Given score detail, when call the api, should update the score detail object`() {

        //given
        val domainData = GamerEntity(id = scoreId, name = "test", matches = "1", scores = "1")
        viewModel.gamerDetail.value = domainData

        val domainDataUpdated = GamerEntity(id = scoreId, name = "test", matches = "10", scores = "10")
        every{ updateGamerUseCase(scoreId,"test", matches = "10", scores = "10") } returns Single.just(domainDataUpdated)

        //when
        viewModel.requestNewGame("test", games = "10", scores = "10")

        //to check the one value for testing
        Assert.assertNotNull(viewModel.gamerDetail)

        Assert.assertEquals(GamerDetailsViewState.ShowContent(domainDataUpdated), viewModel.response.value)
    }

    @Test
    fun `Given score detail null, when call the api, should create the score detail object`() {

        //given
        val domainData = null
        viewModel.gamerDetail.value = domainData

        val domainDataUpdated = GamerEntity(id = scoreId, name = "test", matches = "10", scores = "10")
        every{ createGamerUseCase("test", matches = "10", scores = "10") } returns Single.just(domainDataUpdated)

        //when
        viewModel.requestNewGame("test", games = "10", scores = "10")

        //to check the one value for testing
        Assert.assertNotNull(viewModel.gamerDetail)

        Assert.assertEquals(domainDataUpdated, viewModel.gamerDetail.value)
        Assert.assertEquals(GamerDetailsViewState.ShowContent(domainDataUpdated), viewModel.response.value)
    }

    @Test
    @Throws(Exception::class)
    fun `Given error emission, when load repo score details with error, should update error`() {

        //Given
        val error = RuntimeException("Unknown error")

        every{ createGamerUseCase("test", matches = "10", scores = "10") } returns Single.error(error)

        //when
        viewModel.requestNewGame("test", games = "10", scores = "10")

        //should
        Assert.assertEquals(viewModel.response.value, GamerDetailsViewState.ShowNetworkError(error))
    }
}