package com.rosseti.domain.usecase

import androidx.paging.PagingData
import com.rosseti.domain.entity.ScoreEntity
import com.rosseti.domain.repository.ScoreRepository
import io.reactivex.Flowable

class GetScoresUseCase(private val scoreRepository: ScoreRepository) {

    operator fun invoke(): Flowable<PagingData<ScoreEntity>> = scoreRepository.fetchScores()
}