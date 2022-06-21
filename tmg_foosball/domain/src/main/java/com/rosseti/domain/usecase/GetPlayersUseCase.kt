package com.rosseti.domain.usecase

import com.rosseti.domain.SchedulerProvider
import com.rosseti.domain.entity.PlayerEntity
import com.rosseti.domain.repository.GamerRepository
import io.reactivex.Single

class GetPlayersUseCase(private val schedulers: SchedulerProvider, private val gamerRepository: GamerRepository) {

    operator fun invoke(): Single<List<PlayerEntity>> =
        gamerRepository.fetchPlayers()
            .subscribeOn(schedulers.subscribeOn)
            .observeOn(schedulers.observeOn)
}