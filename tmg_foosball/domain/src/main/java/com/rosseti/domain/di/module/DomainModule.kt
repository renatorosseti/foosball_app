package com.rosseti.domain.di.module

import com.rosseti.domain.SchedulerProvider
import com.rosseti.domain.repository.GameRepository
import com.rosseti.domain.repository.GamerRepository
import com.rosseti.domain.usecase.*
import dagger.Module
import dagger.Provides

@Module
class DomainModule {

    @Provides
    fun provideGetPlayersUseCase(
        schedulerProvider: SchedulerProvider,
        gamerRepository: GamerRepository
    ) = GetPlayersUseCase(schedulerProvider, gamerRepository)

    @Provides
    fun provideUpdatePlayerUseCase(
        schedulerProvider: SchedulerProvider,
        gamerRepository: GamerRepository
    ) = UpdatePlayerUseCase(schedulerProvider, gamerRepository)

    @Provides
    fun provideCreatePlayerUseCase(
        schedulerProvider: SchedulerProvider,
        gamerRepository: GamerRepository
    ) = CreatePlayerUseCase(schedulerProvider, gamerRepository)

    @Provides
    fun provideGetGamesUseCase(
        schedulerProvider: SchedulerProvider,
        gameRepository: GameRepository
    ) = GetGamesUseCase(schedulerProvider, gameRepository)

    @Provides
    fun provideUpdateGameUseCase(
        schedulerProvider: SchedulerProvider,
        gameRepository: GameRepository
    ) = UpdateGameUseCase(schedulerProvider, gameRepository)

    @Provides
    fun provideCreateGameUseCase(
        schedulerProvider: SchedulerProvider,
        gameRepository: GameRepository
    ) = CreateGameUseCase(schedulerProvider, gameRepository)
}