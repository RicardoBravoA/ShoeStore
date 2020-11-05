package com.udacity.shoestore.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.udacity.shoestore.R
import com.udacity.shoestore.databinding.FragmentLoginBinding
import com.udacity.shoestore.utils.CustomTextWatcher
import com.udacity.shoestore.utils.hideKeyboard
import com.udacity.shoestore.utils.showErrorMessageInputLayout
import com.udacity.shoestore.utils.validateErrorInputLayout

class LoginFragment : Fragment() {

    private lateinit var viewModel: LoginViewModel
    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)
        binding.lifecycleOwner = this
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        viewModel.validateEmail.observe(viewLifecycleOwner, { isValidEmail ->
            if (!isValidEmail) {
                binding.emailTextInputLayout.showErrorMessageInputLayout(
                    getString(R.string.error_email)
                )
            }
        })

        viewModel.validatePwd.observe(viewLifecycleOwner, { isValidPwd ->
            if (!isValidPwd) {
                binding.pwdTextInputLayout.showErrorMessageInputLayout(
                    getString(R.string.error_pwd)
                )
            }
        })

        viewModel.validateData.observe(viewLifecycleOwner, { isValid ->
            isValid.getContentIfNotHandled()?.let {
                if (it) {
                    navigate()
                }
            }
        })

        binding.loginButton.setOnClickListener {
            viewModel.validateData(
                binding.emailEdit.text.toString(),
                binding.pwdEdit.text.toString()
            )
        }

        binding.forgotPwdText.setOnClickListener {
            navigate()
        }

        binding.newUserText.setOnClickListener {
            navigate()
        }

        binding.emailEdit.addTextChangedListener(CustomTextWatcher(
            onChanged = { _, _, _, _ ->
                binding.emailTextInputLayout.validateErrorInputLayout()
            }
        ))

        binding.pwdEdit.addTextChangedListener(CustomTextWatcher(
            onChanged = { _, _, _, _ ->
                binding.pwdTextInputLayout.validateErrorInputLayout()
            }
        ))

        binding.pwdEdit.setOnEditorActionListener { _, action, _ ->
            if (action == EditorInfo.IME_ACTION_GO) {
                binding.loginButton.performClick()
            }
            false
        }

        return binding.root
    }

    private fun navigate() {
        hideKeyboard()
        findNavController().navigate(
            LoginFragmentDirections.actionLoginFragmentToWelcomeFragment(
                binding.emailEdit.text.toString()
            )
        )
    }

}