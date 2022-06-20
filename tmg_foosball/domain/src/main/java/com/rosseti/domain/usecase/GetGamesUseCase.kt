package com.rosseti.domain.usecase

import androidx.paging.PagingData
import com.rosseti.domain.SchedulerProvider
import com.rosseti.domain.entity.GameEntity
import com.rosseti.domain.repository.GameRepository
import io.reactivex.Flowable

class GetGamesUseCase(
    private val schedulers: SchedulerProvider,
    private val gameRepository: GameRepository
) {

    operator fun invoke(): Flowable<PagingData<GameEntity>> =
        gameRepository.fetchGames()
            .subscribeOn(schedulers.subscribeOn)
            .observeOn(schedulers.observeOn)
}