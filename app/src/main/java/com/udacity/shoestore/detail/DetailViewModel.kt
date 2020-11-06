package com.udacity.shoestore.detail

import android.text.TextUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.udacity.shoestore.model.detail.AddImageModel
import com.udacity.shoestore.model.detail.ImageModel
import com.udacity.shoestore.model.shoe.ShoeModel
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

    private val _imageList = MutableLiveData<List<Any>>()
    val imageList: LiveData<List<Any>>
        get() = _imageList

    private val _showChips = MutableLiveData<Boolean>()
    val showChips: LiveData<Boolean>
        get() = _showChips

    private val list = mutableListOf<Any>()

    init {
        addImage(AddImageModel())
        _showChips.value = true
    }

    fun validateData(
        name: String,
        description: String,
        company: String,
        size: String?,
        images: List<ImageModel>?
    ) {
        validateName(name)
        validateDescription(description)
        validateCompany(company)
        validateSize(size)

        if (_validateName.value!!
            && _validateDescription.value!!
            && _validateCompany.value!!
            && _validateSize.value!!
        ) {
            _shoe.value =
                SingleEvent(
                    ShoeModel(
                        name,
                        description,
                        company,
                        size!!.toDouble(),
                        images ?: arrayListOf()
                    )
                )
        }
    }

    fun addImage(image: Any) {
        list.add(image)
        _imageList.value = list
    }

    fun completeSizeErrorMessage() {
        _validateSize.value = true
    }

    fun validateName(value: String) {
        _validateName.value = !TextUtils.isEmpty(value)
    }

    fun validateDescription(value: String) {
        _validateDescription.value = !TextUtils.isEmpty(value)
    }

    fun validateCompany(value: String) {
        _validateCompany.value = !TextUtils.isEmpty(value)
    }

    private fun validateSize(value: String?) {
        _validateSize.value = value != null && !TextUtils.isEmpty(value)
    }

    fun images(): List<ImageModel>? {
        return _imageList.value?.filterIsInstance<ImageModel>()
    }

}