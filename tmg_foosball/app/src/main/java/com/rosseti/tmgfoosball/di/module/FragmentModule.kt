package com.rosseti.tmgfoosball.di.module

import com.rosseti.tmgfoosball.scores.ScoreDetailsFragment
import com.rosseti.tmgfoosball.scores.ScoreListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentModule {

    @ContributesAndroidInjector
    abstract fun scoreListFragment() : ScoreListFragment

    @ContributesAndroidInjector
    abstract fun scoreDetailsFragment() : ScoreDetailsFragment
}