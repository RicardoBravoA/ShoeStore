package com.udacity.shoestore.walkthrough

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.udacity.shoestore.utils.SingleEvent

class WalkthroughViewModel(user: String?) : ViewModel() {

    private val _email = MutableLiveData<String>()
    val email: LiveData<String>
        get() = _email

    private val _navigation = MutableLiveData<SingleEvent<Boolean>>()
    val navigation: LiveData<SingleEvent<Boolean>>
        get() = _navigation

    private val _showData = MutableLiveData<Boolean>()
    val showData: LiveData<Boolean>
        get() = _showData

    init {
        _email.value = user
        _showData.value = true
    }

    fun navigation() {
        _navigation.value = SingleEvent(true)
    }

}