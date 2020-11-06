package com.udacity.shoestore.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.udacity.shoestore.model.shoe.ShoeModel
import com.udacity.shoestore.utils.SingleEvent

class ListViewModel : ViewModel() {

    private val _shoe = MutableLiveData<Pair<Int, ShoeModel>>()
    val shoe: LiveData<Pair<Int, ShoeModel>>
        get() = _shoe

    private val data = mutableListOf<ShoeModel>()

    private val _navigation = MutableLiveData<SingleEvent<Boolean>>()
    val navigation: LiveData<SingleEvent<Boolean>>
        get() = _navigation

    fun addShoe(shoe: ShoeModel) {
        _shoe.value = Pair(data.size, shoe)
        data.add(shoe)
    }

    fun navigation() {
        _navigation.value = SingleEvent(true)
    }

}