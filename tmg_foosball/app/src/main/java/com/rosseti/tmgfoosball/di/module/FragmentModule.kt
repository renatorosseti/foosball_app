package com.rosseti.tmgfoosball.di.module

import com.rosseti.tmgfoosball.ui.detail.ScoreDetailsFragment
import com.rosseti.tmgfoosball.ui.list.ResultListFragment
import com.rosseti.tmgfoosball.ui.list.ScoreListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentModule {

    @ContributesAndroidInjector
    abstract fun scoreListFragment() : ScoreListFragment

    @ContributesAndroidInjector
    abstract fun scoreDetailsFragment() : ScoreDetailsFragment

    @ContributesAndroidInjector
    abstract fun resultListFragment() : ResultListFragment
}