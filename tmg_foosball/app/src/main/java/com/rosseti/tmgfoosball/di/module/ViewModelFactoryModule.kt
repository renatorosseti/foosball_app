package com.rosseti.tmgfoosball.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rosseti.tmgfoosball.ui.detail.GamerDetailsViewModel
import com.rosseti.tmgfoosball.ui.list.GameListViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelFactoryModule {

    @Binds
    abstract fun bindViewModelFactory(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(GameListViewModel::class)
    abstract fun provideScoreViewModel(viewModel: GameListViewModel) : ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(GamerDetailsViewModel::class)
    abstract fun provideScoreDetailsViewModel(viewModel: GamerDetailsViewModel) : ViewModel
}