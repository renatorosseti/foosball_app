package com.rosseti.tmgfoosball.di.module

import com.rosseti.tmgfoosball.ui.detail.PlayerDetailsFragment
import com.rosseti.tmgfoosball.ui.detail.GameDetailsFragment
import com.rosseti.tmgfoosball.ui.list.GamerListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentModule {

    @ContributesAndroidInjector
    abstract fun scoreListFragment() : GamerListFragment

    @ContributesAndroidInjector
    abstract fun scoreDetailsFragment() : PlayerDetailsFragment

    @ContributesAndroidInjector
    abstract fun resultListFragment() : GameDetailsFragment
}