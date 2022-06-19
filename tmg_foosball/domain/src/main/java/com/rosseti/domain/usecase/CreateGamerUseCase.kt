package com.rosseti.domain.usecase

import com.rosseti.domain.SchedulerProvider
import com.rosseti.domain.entity.GameEntity
import com.rosseti.domain.entity.GamerEntity
import com.rosseti.domain.repository.GamerRepository
import io.reactivex.Single

class CreateGamerUseCase(
    private val schedulers: SchedulerProvider,
    private val gamerRepository: GamerRepository
) {

    operator fun invoke(
        name: String,
        games: List<GameEntity>
    ): Single<GamerEntity> =
        gamerRepository.createGamer(name, games)
            .subscribeOn(schedulers.subscribeOn)
            .observeOn(schedulers.observeOn)
}