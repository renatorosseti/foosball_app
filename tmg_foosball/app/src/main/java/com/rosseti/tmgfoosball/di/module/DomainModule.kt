package com.rosseti.tmgfoosball.di.module

import com.rosseti.domain.repository.ScoreRepository
import com.rosseti.domain.usecase.GetScoresUseCase
import dagger.Module
import dagger.Provides

@Module
 class DomainModule {

    @Provides
    fun provideGetScoresUseCase(scoreRepository: ScoreRepository)
            = GetScoresUseCase(scoreRepository)
}