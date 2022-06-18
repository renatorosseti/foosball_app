package com.rosseti.tmgfoosball.di.module

import com.rosseti.data.api.Api
import com.rosseti.data.repository.ScoreRepositoryImpl
import com.rosseti.domain.repository.ScoreRepository
import dagger.Module
import dagger.Provides

@Module
class DataModule {

    @Provides
    fun provideScoreRepository(api: Api): ScoreRepository
            = ScoreRepositoryImpl(api)
}