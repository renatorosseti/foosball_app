package com.rosseti.tmgfoosball.di.module

import com.rosseti.domain.SchedulerProvider
import com.rosseti.domain.repository.GameRepository
import com.rosseti.domain.repository.GamerRepository
import com.rosseti.domain.usecase.*
import dagger.Module
import dagger.Provides

@Module
class DomainModule {

    @Provides
    fun provideGetScoresUseCase(
        schedulerProvider: SchedulerProvider,
        gamerRepository: GamerRepository
    ) = GetGamersUseCase(schedulerProvider, gamerRepository)

    @Provides
    fun provideUpdateScoreUseCase(
        schedulerProvider: SchedulerProvider,
        gamerRepository: GamerRepository
    ) = UpdateGamerUseCase(schedulerProvider, gamerRepository)

    @Provides
    fun provideCreateScoreUseCase(
        schedulerProvider: SchedulerProvider,
        gamerRepository: GamerRepository
    ) = CreateGamerUseCase(schedulerProvider, gamerRepository)

    @Provides
    fun provideGetGamesUseCase(
        schedulerProvider: SchedulerProvider,
        gameRepository: GameRepository
    ) = GetGamesUseCase(schedulerProvider, gameRepository)

    @Provides
    fun provideUpdateScoreUseCase(
        schedulerProvider: SchedulerProvider,
        gameRepository: GameRepository
    ) = UpdateGameUseCase(schedulerProvider, gameRepository)

    @Provides
    fun provideCreateScoreUseCase(
        schedulerProvider: SchedulerProvider,
        gameRepository: GameRepository
    ) = CreateGameUseCase(schedulerProvider, gameRepository)
}