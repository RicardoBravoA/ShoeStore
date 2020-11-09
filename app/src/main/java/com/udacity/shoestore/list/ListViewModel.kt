package com.udacity.shoestore.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.udacity.shoestore.model.shoe.ShoeModel
import com.udacity.shoestore.utils.SingleEvent

class ListViewModel : ViewModel() {

    private val _shoeList = MutableLiveData<SingleEvent<MutableList<ShoeModel>>>()
    val shoeList: LiveData<SingleEvent<MutableList<ShoeModel>>>
        get() = _shoeList

    private val data = mutableListOf<ShoeModel>()

    private val _navigation = MutableLiveData<SingleEvent<Boolean>>()
    val navigation: LiveData<SingleEvent<Boolean>>
        get() = _navigation

    fun addShoe(shoe: ShoeModel) {
        data.add(shoe)
        _shoeList.value = SingleEvent(data)
    }

    fun navigation() {
        _navigation.value = SingleEvent(true)
    }

}