package com.rosseti.tmgfoosball.ui.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.rosseti.domain.entity.ScoreEntity
import com.rosseti.domain.usecase.CreateScoreUseCase
import com.rosseti.domain.usecase.GetScoreDetailsUseCase
import com.rosseti.domain.usecase.UpdateScoreUseCase
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.reactivex.Single
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class ScoreDetailsViewModelTest {
    private lateinit var viewModel: ScoreDetailsViewModel

    @MockK
    lateinit var getScoreDetailsUseCase: GetScoreDetailsUseCase

    @MockK
    lateinit var updateScoreUseCase: UpdateScoreUseCase

    @MockK
    lateinit var createScoreUseCase: CreateScoreUseCase

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private val scoreId = 100

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        viewModel = ScoreDetailsViewModel(getScoreDetailsUseCase, updateScoreUseCase, createScoreUseCase)
    }

    @Test
    fun `Given score detail, when call the api, should update the score detail object`() {

        //given
        val domainData = ScoreEntity(id = scoreId, name = "test", matches = "1", scores = "1")
        viewModel.scoreDetail.value = domainData

        val domainDataUpdated = ScoreEntity(id = scoreId, name = "test", matches = "10", scores = "10")
        every{ updateScoreUseCase(scoreId,"test", matches = "10", scores = "10") } returns Single.just(domainDataUpdated)

        //when
        viewModel.requestScore("test", matches = "10", scores = "10")

        //to check the one value for testing
        Assert.assertNotNull(viewModel.scoreDetail)

        Assert.assertEquals(ScoreDetailsViewState.ShowContent(domainDataUpdated), viewModel.response.value)
    }

    @Test
    fun `Given score detail null, when call the api, should create the score detail object`() {

        //given
        val domainData = null
        viewModel.scoreDetail.value = domainData

        val domainDataUpdated = ScoreEntity(id = scoreId, name = "test", matches = "10", scores = "10")
        every{ createScoreUseCase("test", matches = "10", scores = "10") } returns Single.just(domainDataUpdated)

        //when
        viewModel.requestScore("test", matches = "10", scores = "10")

        //to check the one value for testing
        Assert.assertNotNull(viewModel.scoreDetail)

        Assert.assertEquals(domainDataUpdated, viewModel.scoreDetail.value)
        Assert.assertEquals(ScoreDetailsViewState.ShowContent(domainDataUpdated), viewModel.response.value)
    }

    @Test
    @Throws(Exception::class)
    fun `Given error emission, when load repo score details with error, should update error`() {

        //Given
        val error = RuntimeException("Unknown error")

        every{ createScoreUseCase("test", matches = "10", scores = "10") } returns Single.error(error)

        //when
        viewModel.requestScore("test", matches = "10", scores = "10")

        //should
        Assert.assertEquals(viewModel.response.value, ScoreDetailsViewState.ShowNetworkError(error))
    }
}