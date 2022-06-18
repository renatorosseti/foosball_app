package com.rosseti.domain.usecase

import com.rosseti.domain.SchedulerProvider
import com.rosseti.domain.entity.ScoreEntity
import com.rosseti.domain.repository.ScoreRepository
import io.reactivex.Single

class CreateScoreUseCase(
    private val schedulers: SchedulerProvider,
    private val scoreRepository: ScoreRepository
) {

    operator fun invoke(
        name: String,
        matches: String,
        scores: String
    ): Single<ScoreEntity> =
        scoreRepository.createScore(name, matches, scores)
            .subscribeOn(schedulers.subscribeOn)
            .observeOn(schedulers.observeOn)
}