package com.rosseti.tmgfoosball.di.module

import com.rosseti.data.api.Api
import com.rosseti.data.repository.GamerRepositoryImpl
import com.rosseti.domain.repository.GamerRepository
import dagger.Module
import dagger.Provides

@Module
class DataModule {

    @Provides
    fun provideScoreRepository(api: Api): GamerRepository = GamerRepositoryImpl(api)
}