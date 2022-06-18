package com.rosseti.tmgfoosball.di.module

import com.rosseti.tmgfoosball.ui.dialog.MessageDialog
import com.rosseti.tmgfoosball.ui.dialog.ProgressDialog
import dagger.Module
import dagger.Provides

@Module
class DialogModule {
    @Provides
    fun providesErrorDialog(): MessageDialog = MessageDialog()

    @Provides
    fun providesProgressDialog(): ProgressDialog = ProgressDialog()
}