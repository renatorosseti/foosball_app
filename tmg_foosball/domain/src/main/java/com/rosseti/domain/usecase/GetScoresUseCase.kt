package com.rosseti.domain.usecase

import androidx.paging.PagingData
import com.rosseti.domain.SchedulerProvider
import com.rosseti.domain.entity.ScoreEntity
import com.rosseti.domain.repository.ScoreRepository
import io.reactivex.Flowable

class GetScoresUseCase(private val schedulers: SchedulerProvider, private val scoreRepository: ScoreRepository) {

    operator fun invoke(): Flowable<PagingData<ScoreEntity>> =
        scoreRepository.fetchScores()
            .subscribeOn(schedulers.subscribeOn)
            .observeOn(schedulers.observeOn)
}