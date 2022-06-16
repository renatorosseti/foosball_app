package com.rosseti.tmgfoosball.di.module

import android.app.Application
import android.content.Context
import com.rosseti.domain.SchedulerProvider
import com.rosseti.tmgfoosball.di.module.scheduler.AppSchedulers
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

@Module
class AppModule {
    @Provides
    @Singleton
    @Named("application.Context")
    fun provideContext(application: Application) : Context {
        return application.applicationContext
    }

    @Provides
    @Singleton
    fun provideSchedulers() : SchedulerProvider = AppSchedulers()
}