package com.rosseti.tmgfoosball.extension

import android.content.Context
import com.google.android.material.textfield.TextInputLayout
import com.rosseti.tmgfoosball.R

fun TextInputLayout.setupInputError(isValid: Boolean, context: Context) {
    error = context.getString(R.string.error_required_field)
    isErrorEnabled = !isValid
}