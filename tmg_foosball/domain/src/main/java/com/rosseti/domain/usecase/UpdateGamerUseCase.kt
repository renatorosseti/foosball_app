package com.rosseti.domain.usecase

import com.rosseti.domain.SchedulerProvider
import com.rosseti.domain.entity.GameEntity
import com.rosseti.domain.entity.GamerEntity
import com.rosseti.domain.repository.GamerRepository
import io.reactivex.Single

class UpdateGamerUseCase(
    private val schedulers: SchedulerProvider,
    private val gamerRepository: GamerRepository
) {

    operator fun invoke(
        id: Int,
        name: String
    ): Single<GamerEntity> =
        gamerRepository.updateGamer(id, name)
            .subscribeOn(schedulers.subscribeOn)
            .observeOn(schedulers.observeOn)
}