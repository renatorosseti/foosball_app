package com.rosseti.domain.usecase

import com.rosseti.domain.SchedulerProvider
import com.rosseti.domain.entity.PlayerEntity
import com.rosseti.domain.repository.GamerRepository
import io.reactivex.Single

class CreatePlayerUseCase(
    private val schedulers: SchedulerProvider,
    private val gamerRepository: GamerRepository
) {
    operator fun invoke(
        name: String
    ): Single<PlayerEntity> =
        gamerRepository.createPlayer(name)
            .subscribeOn(schedulers.subscribeOn)
            .observeOn(schedulers.observeOn)
}