package com.rosseti.domain.usecase

import com.rosseti.domain.SchedulerProvider
import com.rosseti.domain.entity.ScoreEntity
import com.rosseti.domain.repository.ScoreRepository
import io.reactivex.Single

class UpdateScoreUseCase(
    private val schedulers: SchedulerProvider,
    private val scoreRepository: ScoreRepository
) {

    operator fun invoke(
        scoreId: Int,
        name: String,
        matches: String,
        scores: String
    ): Single<ScoreEntity> =
        scoreRepository.updateScore(scoreId, name, matches, scores)
            .subscribeOn(schedulers.subscribeOn)
            .observeOn(schedulers.observeOn)
}