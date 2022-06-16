package com.rosseti.tmgfoosball.di.module

import com.rosseti.tmgfoosball.scores.ScoreFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentModule {

    @ContributesAndroidInjector
    abstract fun scoreFragment() : ScoreFragment
}