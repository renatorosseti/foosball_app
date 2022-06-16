package com.rosseti.tmgfoosball.di.component

import android.app.Application
import android.content.Context
import com.rosseti.data.di.module.NetworkModule
import com.rosseti.tmgfoosball.TMGFoosballApp
import com.rosseti.tmgfoosball.di.module.*
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        AndroidInjectionModule::class,
        ViewModelFactoryModule::class,
        FragmentModule::class,
        DomainModule::class,
        NetworkModule::class,
        DataModule::class]
)

interface AppComponent {

    fun inject(application: TMGFoosballApp)

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        @BindsInstance
        fun context(context: Context): Builder

        fun build(): AppComponent
    }
}