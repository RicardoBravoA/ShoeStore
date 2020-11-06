package com.udacity.shoestore.utils

import com.google.android.material.textfield.TextInputLayout

fun TextInputLayout.validateErrorInputLayout() {
    if (isErrorEnabled) {
        error = null
    }
}

fun TextInputLayout.showErrorMessageInputLayout(message: String) {
    isErrorEnabled = true
    error = message
}