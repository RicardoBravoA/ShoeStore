package com.udacity.shoestore.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.udacity.shoestore.models.ShoeModel
import com.udacity.shoestore.utils.NavigationEvent

class ListViewModel : ViewModel() {

    private val _shoeList = MutableLiveData<List<ShoeModel>>()
    val shoeList: LiveData<List<ShoeModel>>
        get() = _shoeList
    private val data = mutableListOf<ShoeModel>()

    private val _navigation = MutableLiveData<NavigationEvent<Boolean>>()
    val navigation: LiveData<NavigationEvent<Boolean>>
        get() = _navigation

    fun addShoe(shoe: ShoeModel) {
        data.add(shoe)
        _shoeList.value = data
    }

    fun addShoeList(shoe: List<ShoeModel>) {
        data.clear()
        data.addAll(shoe)
        _shoeList.value = data
    }

    fun navigation() {
        _navigation.value = NavigationEvent(true)
    }

}