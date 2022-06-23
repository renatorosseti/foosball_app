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
        adversaryId: String,
        adversaryName: String,
        playerName: String,
        score: String,
        scoreAdversary: String
    ): Single<GameEntity> =
        gameRepository.updateGame(
            id = id,
            playerId = gamerId,
            adversaryId = adversaryId,
            playerName = playerName,
            adversaryName = adversaryName,
            score = score,
            scoreAdversary = scoreAdversary
        )
            .subscribeOn(schedulers.subscribeOn)
            .observeOn(schedulers.observeOn)
}