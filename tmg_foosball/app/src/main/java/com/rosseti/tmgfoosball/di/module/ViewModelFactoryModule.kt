package com.rosseti.tmgfoosball.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rosseti.tmgfoosball.ui.detail.GameDetailsViewModel
import com.rosseti.tmgfoosball.ui.list.PlayerListViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelFactoryModule {

    @Binds
    abstract fun bindViewModelFactory(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(PlayerListViewModel::class)
    abstract fun provideScoreViewModel(viewModel: PlayerListViewModel) : ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(GameDetailsViewModel::class)
    abstract fun provideScoreDetailsViewModel(viewModel: GameDetailsViewModel) : ViewModel
}