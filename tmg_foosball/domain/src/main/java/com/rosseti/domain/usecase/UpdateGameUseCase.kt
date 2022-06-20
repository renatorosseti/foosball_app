package com.rosseti.domain.usecase

import com.rosseti.domain.SchedulerProvider
import com.rosseti.domain.entity.GameEntity
import com.rosseti.domain.repository.GameRepository
import io.reactivex.Single

class UpdateGameUseCase(
    private val schedulers: SchedulerProvider,
    private val gameRepository: GameRepository
) {

    operator fun invoke(
        id: String,
        gamerId: String,
        adversary: String,
        score: Int,
        scoreAdversary: Int
    ): Single<GameEntity> =
        gameRepository.updateGame(id, gamerId, adversary, score, scoreAdversary)
            .subscribeOn(schedulers.subscribeOn)
            .observeOn(schedulers.observeOn)
}