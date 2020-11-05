package com.udacity.shoestore.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.udacity.shoestore.utils.NavigationEvent

class DetailViewModel : ViewModel() {

    private val _navigation = MutableLiveData<NavigationEvent<Boolean>>()
    val navigation: LiveData<NavigationEvent<Boolean>>
        get() = _navigation

    fun navigation() {
        _navigation.value = NavigationEvent(true)
    }

}