package com.udacity.shoestore.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.udacity.shoestore.utils.InputUtils

class LoginViewModel : ViewModel() {

    init {
        Log.i("LoginViewModel", "init")
    }

    private val _validateData = MutableLiveData<Boolean>()
    val validateData: LiveData<Boolean>
        get() = _validateData

    private val _validateEmail = MutableLiveData<Boolean>()
    val validateEmail: LiveData<Boolean>
        get() = _validateEmail

    private val _validatePwd = MutableLiveData<Boolean>()
    val validatePwd: LiveData<Boolean>
        get() = _validatePwd

    val emailAddress = MutableLiveData<String>("")

    /*val valid = MediatorLiveData<Boolean>().apply {
        addSource(emailAddress) {
            val valid = InputUtils.isValidEmail(it)
            Log.d(it, valid.toString())
            value = valid
        }
    }*/

    fun validateData(email: String, pwd: String) {
        _validateEmail.value = InputUtils.isValidEmail(email)
        _validatePwd.value = InputUtils.isValidPwd(pwd)
        _validateData.value = _validateEmail.value!! && _validatePwd.value!!
    }

}