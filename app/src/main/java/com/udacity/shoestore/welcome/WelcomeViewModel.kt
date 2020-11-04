package com.udacity.shoestore.welcome

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.udacity.shoestore.utils.NavigationEvent

class WelcomeViewModel(user: String) : ViewModel() {

    private val _email = MutableLiveData<String>()
    val email: LiveData<String>
        get() = _email

    private val _navigation = MutableLiveData<NavigationEvent<Boolean>>()
    val navigation: LiveData<NavigationEvent<Boolean>>
        get() = _navigation

    init {
        _email.value = user
    }

    fun navigation() {
        _navigation.value = NavigationEvent(true)
    }

}