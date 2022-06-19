package com.rosseti.domain.usecase

import androidx.paging.PagingData
import com.rosseti.domain.SchedulerProvider
import com.rosseti.domain.entity.GamerEntity
import com.rosseti.domain.repository.GamerRepository
import io.reactivex.Flowable

class GetGamersUseCase(private val schedulers: SchedulerProvider, private val gamerRepository: GamerRepository) {

    operator fun invoke(): Flowable<PagingData<GamerEntity>> =
        gamerRepository.fetchGamers()
            .subscribeOn(schedulers.subscribeOn)
            .observeOn(schedulers.observeOn)
}