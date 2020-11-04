package com.udacity.shoestore.utils

import android.text.TextUtils
import android.util.Patterns

object InputUtils {

    private const val SIZE_PWD = 8

    fun isValidEmail(email: String): Boolean {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun isValidPwd(pwd: String): Boolean {
        return !TextUtils.isEmpty(pwd) && pwd.length >= SIZE_PWD
    }

}