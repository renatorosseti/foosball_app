package com.rosseti.tmgfoosball.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rosseti.tmgfoosball.ui.detail.ScoreDetailsViewModel
import com.rosseti.tmgfoosball.ui.list.ScoreListViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelFactoryModule {

    @Binds
    abstract fun bindViewModelFactory(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(ScoreListViewModel::class)
    abstract fun provideScoreViewModel(viewModel: ScoreListViewModel) : ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ScoreDetailsViewModel::class)
    abstract fun provideScoreDetailsViewModel(viewModel: ScoreDetailsViewModel) : ViewModel
}