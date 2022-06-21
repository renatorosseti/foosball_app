package com.rosseti.domain.usecase

import com.rosseti.domain.SchedulerProvider
import com.rosseti.domain.entity.GameEntity
import com.rosseti.domain.repository.GameRepository
import io.reactivex.Single

class GetGamesUseCase(
    private val schedulers: SchedulerProvider,
    private val gameRepository: GameRepository
) {

    operator fun invoke(): Single<List<GameEntity>> =
        gameRepository.fetchGames()
            .subscribeOn(schedulers.subscribeOn)
            .observeOn(schedulers.observeOn)
}