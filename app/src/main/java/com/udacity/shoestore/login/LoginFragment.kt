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
import com.google.android.material.textfield.TextInputLayout
import com.udacity.shoestore.R
import com.udacity.shoestore.databinding.FragmentLoginBinding
import com.udacity.shoestore.utils.CustomTextWatcher

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
                binding.emailTextInputLayout.isErrorEnabled = true
                binding.emailTextInputLayout.error = getString(R.string.error_email)
            }
        })

        viewModel.validatePwd.observe(viewLifecycleOwner, { isValidPwd ->
            if (!isValidPwd) {
                binding.pwdTextInputLayout.isErrorEnabled = true
                binding.pwdTextInputLayout.error = getString(R.string.error_pwd)
            }
        })

        viewModel.validateData.observe(viewLifecycleOwner, { isValid ->
            if (isValid) {
                findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToWelcomeFragment())
            }
        })

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
                validateErrorInputLayout(binding.emailTextInputLayout)
            }
        ))

        binding.pwdEdit.addTextChangedListener(CustomTextWatcher(
            onChanged = { _, _, _, _ ->
                validateErrorInputLayout(binding.pwdTextInputLayout)
            }
        ))

        binding.pwdEdit.setOnEditorActionListener { _, action, _ ->
            if (action == EditorInfo.IME_ACTION_GO) {
                binding.loginButton.performClick()
            }
            false
        }

    }

    private fun navigate() {
        findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToWelcomeFragment())
    }

    private fun validateErrorInputLayout(textInputLayout: TextInputLayout) {
        if (textInputLayout.isErrorEnabled) {
            textInputLayout.error = null
        }
    }

}