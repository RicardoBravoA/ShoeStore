package com.udacity.shoestore.walkthrough

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

@Suppress("UNCHECKED_CAST")
class WalkthroughViewModelFactory(private val email: String?) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WalkthroughViewModel::class.java)) {
            return WalkthroughViewModel(email) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
