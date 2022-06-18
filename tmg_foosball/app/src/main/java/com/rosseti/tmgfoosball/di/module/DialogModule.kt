package com.rosseti.tmgfoosball.di.module

import com.rosseti.tmgfoosball.ui.dialog.ErrorDialog
import com.rosseti.tmgfoosball.ui.dialog.ProgressDialog
import dagger.Module
import dagger.Provides

@Module
class DialogModule {
    @Provides
    fun providesErrorDialog(): ErrorDialog = ErrorDialog()

    @Provides
    fun providesProgressDialog(): ProgressDialog = ProgressDialog()
}