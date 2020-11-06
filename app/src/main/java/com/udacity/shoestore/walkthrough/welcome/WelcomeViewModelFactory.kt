package com.udacity.shoestore.walkthrough.welcome

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

@Suppress("UNCHECKED_CAST")
class WelcomeViewModelFactory(private val email: String) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WelcomeViewModel::class.java)) {
            return WelcomeViewModel(email) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
