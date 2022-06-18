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
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
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
    fun `Given score detail, when call the api ,should update the score detail object`() {

        //given
        val domainData = ScoreEntity(id = scoreId, name = "test", matches = "1", scores = "1")

        every{ getScoreDetailsUseCase(scoreId) } returns Single.just(domainData)

        //when
        viewModel.getScoreDetailById(scoreId)

        //to check the one value for testing
        val expectedValue = 100
        Assert.assertNotNull(viewModel.scoreDetail)

        Assert.assertEquals(expectedValue, viewModel.scoreDetail.value?.id)
    }
}