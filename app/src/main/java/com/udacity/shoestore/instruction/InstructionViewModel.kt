package com.udacity.shoestore.instruction

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.udacity.shoestore.utils.SingleEvent

class InstructionViewModel : ViewModel() {

    private val _navigation = MutableLiveData<SingleEvent<Boolean>>()
    val navigation: LiveData<SingleEvent<Boolean>>
        get() = _navigation

    fun navigation() {
        _navigation.value = SingleEvent(true)
    }

}