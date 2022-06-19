package com.rosseti.tmgfoosball.di.module

import com.rosseti.domain.SchedulerProvider
import com.rosseti.domain.repository.GamerRepository
import com.rosseti.domain.usecase.CreateGamerUseCase
import com.rosseti.domain.usecase.GetGamersUseCase
import com.rosseti.domain.usecase.UpdateGamerUseCase
import dagger.Module
import dagger.Provides

@Module
 class DomainModule {

    @Provides
    fun provideGetScoresUseCase(schedulerProvider: SchedulerProvider, gamerRepository: GamerRepository)
            = GetGamersUseCase(schedulerProvider, gamerRepository)

    @Provides
    fun provideUpdateScoreUseCase(schedulerProvider: SchedulerProvider, gamerRepository: GamerRepository)
            = UpdateGamerUseCase(schedulerProvider, gamerRepository)

    @Provides
    fun provideCreateScoreUseCase(schedulerProvider: SchedulerProvider, gamerRepository: GamerRepository)
            = CreateGamerUseCase(schedulerProvider, gamerRepository)
}