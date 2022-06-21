package com.rosseti.domain.usecase

import com.rosseti.domain.SchedulerProvider
import com.rosseti.domain.entity.GameEntity
import com.rosseti.domain.repository.GameRepository
import io.reactivex.Single

class CreateGameUseCase(
    private val schedulers: SchedulerProvider,
    private val gameRepository: GameRepository
) {
    operator fun invoke(
        gamerId: String,
        adversary: String,
        score: String,
        scoreAdversary: String
    ): Single<GameEntity> =
        gameRepository.createGame(gamerId, adversary, score, scoreAdversary)
            .subscribeOn(schedulers.subscribeOn)
            .observeOn(schedulers.observeOn)
}