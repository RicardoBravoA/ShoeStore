package com.udacity.shoestore.instruction

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.udacity.shoestore.utils.NavigationEvent

class InstructionViewModel : ViewModel() {

    private val _navigation = MutableLiveData<NavigationEvent<Boolean>>()
    val navigation: LiveData<NavigationEvent<Boolean>>
        get() = _navigation

    fun navigation() {
        _navigation.value = NavigationEvent(true)
    }

}