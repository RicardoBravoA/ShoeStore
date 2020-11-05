package com.udacity.shoestore.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.udacity.shoestore.utils.SingleEvent
import com.udacity.shoestore.utils.InputUtils

class LoginViewModel : ViewModel() {

    private val _validateData = MutableLiveData<SingleEvent<Boolean>>()
    val validateData: LiveData<SingleEvent<Boolean>>
        get() = _validateData

    private val _validateEmail = MutableLiveData<Boolean>()
    val validateEmail: LiveData<Boolean>
        get() = _validateEmail

    private val _validatePwd = MutableLiveData<Boolean>()
    val validatePwd: LiveData<Boolean>
        get() = _validatePwd

    fun validateData(email: String, pwd: String) {
        _validateEmail.value = InputUtils.isValidEmail(email)
        _validatePwd.value = InputUtils.isValidPwd(pwd)
        _validateData.value = SingleEvent(_validateEmail.value!! && _validatePwd.value!!)
    }

}