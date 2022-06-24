package com.rosseti.data.di.module

import com.rosseti.data.api.Api
import com.rosseti.data.repository.GameRepositoryImpl
import com.rosseti.data.repository.GamerRepositoryImpl
import com.rosseti.domain.repository.GameRepository
import com.rosseti.domain.repository.GamerRepository
import dagger.Module
import dagger.Provides

@Module
class DataModule {

    @Provides
    fun provideGamerRepository(api: Api): GamerRepository = GamerRepositoryImpl(api)

    @Provides
    fun provideGameRepository(api: Api): GameRepository = GameRepositoryImpl(api)
}