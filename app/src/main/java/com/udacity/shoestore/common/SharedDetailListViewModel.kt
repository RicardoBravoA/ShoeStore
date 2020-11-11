package com.udacity.shoestore.common

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.udacity.shoestore.model.shoe.ShoeModel

class SharedDetailListViewModel : ViewModel() {

    private val _shoe = MutableLiveData<ShoeModel>()
    val shoe: LiveData<ShoeModel>
        get() = _shoe

    fun addShoe(shoe: ShoeModel) {
        _shoe.value = shoe
    }
}