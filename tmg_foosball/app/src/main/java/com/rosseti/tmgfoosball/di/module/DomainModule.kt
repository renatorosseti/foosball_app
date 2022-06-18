package com.rosseti.tmgfoosball.di.module

import com.rosseti.domain.SchedulerProvider
import com.rosseti.domain.repository.ScoreRepository
import com.rosseti.domain.usecase.CreateScoreUseCase
import com.rosseti.domain.usecase.GetScoreDetailsUseCase
import com.rosseti.domain.usecase.GetScoresUseCase
import com.rosseti.domain.usecase.UpdateScoreUseCase
import dagger.Module
import dagger.Provides

@Module
 class DomainModule {

    @Provides
    fun provideGetScoresUseCase(schedulerProvider: SchedulerProvider, scoreRepository: ScoreRepository)
            = GetScoresUseCase(schedulerProvider, scoreRepository)

    @Provides
    fun provideUpdateScoreUseCase(schedulerProvider: SchedulerProvider, scoreRepository: ScoreRepository)
            = UpdateScoreUseCase(schedulerProvider, scoreRepository)

    @Provides
    fun provideCreateScoreUseCase(schedulerProvider: SchedulerProvider, scoreRepository: ScoreRepository)
            = CreateScoreUseCase(schedulerProvider, scoreRepository)

    @Provides
    fun provideGetScoreDetailsUseCase(schedulerProvider: SchedulerProvider, scoreRepository: ScoreRepository)
            = GetScoreDetailsUseCase(schedulerProvider, scoreRepository)
}