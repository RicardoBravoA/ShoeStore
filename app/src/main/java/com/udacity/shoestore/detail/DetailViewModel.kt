package com.udacity.shoestore.detail

import android.text.TextUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.udacity.shoestore.model.ShoeModel
import com.udacity.shoestore.utils.SingleEvent

class DetailViewModel : ViewModel() {

    private val _validateName = MutableLiveData<Boolean>()
    val validateName: LiveData<Boolean>
        get() = _validateName

    private val _validateDescription = MutableLiveData<Boolean>()
    val validateDescription: LiveData<Boolean>
        get() = _validateDescription

    private val _validateCompany = MutableLiveData<Boolean>()
    val validateCompany: LiveData<Boolean>
        get() = _validateCompany

    private val _validateSize = MutableLiveData<Boolean>()
    val validateSize: LiveData<Boolean>
        get() = _validateSize

    private val _shoe = MutableLiveData<SingleEvent<ShoeModel>>()
    val shoe: LiveData<SingleEvent<ShoeModel>>
        get() = _shoe

    fun validateData(name: String, description: String, company: String, size: String?) {
        _validateName.value = !TextUtils.isEmpty(name)
        _validateDescription.value = !TextUtils.isEmpty(description)
        _validateCompany.value = !TextUtils.isEmpty(company)
        _validateSize.value = size != null && !TextUtils.isEmpty(size)

        if (_validateName.value!!
            && _validateDescription.value!!
            && _validateCompany.value!!
            && _validateSize.value!!
        ) {
            _shoe.value = SingleEvent(ShoeModel(name, size!!.toDouble(), company, description))
        }
    }

}