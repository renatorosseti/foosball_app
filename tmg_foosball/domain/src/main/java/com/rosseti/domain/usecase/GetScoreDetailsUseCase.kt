package com.rosseti.domain.usecase

import com.rosseti.domain.SchedulerProvider
import com.rosseti.domain.entity.ScoreEntity
import com.rosseti.domain.repository.ScoreRepository
import io.reactivex.Single

class GetScoreDetailsUseCase(
    private val schedulers: SchedulerProvider,
    private val scoreRepository: ScoreRepository
) {

    operator fun invoke(scoreId: String): Single<ScoreEntity> =
        scoreRepository.fetchScoreById(scoreId)
            .subscribeOn(schedulers.subscribeOn)
            .observeOn(schedulers.observeOn)
}