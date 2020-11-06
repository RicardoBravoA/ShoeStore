package com.udacity.shoestore.walkthrough.welcome

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.udacity.shoestore.R
import com.udacity.shoestore.databinding.FragmentWelcomeBinding
import com.udacity.shoestore.walkthrough.WalkthroughViewModelFactory

class WelcomeFragment : Fragment() {

    private lateinit var viewModel: WelcomeViewModel
    private lateinit var viewModelFactory: WalkthroughViewModelFactory
    private var email: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentWelcomeBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_welcome,
            container,
            false
        )

        arguments?.let {
            email = it.getString(KEY)
        }

        viewModelFactory = WalkthroughViewModelFactory(email)
        viewModel = ViewModelProvider(this, viewModelFactory).get(WelcomeViewModel::class.java)

        binding.welcomeViewModel = viewModel
        binding.lifecycleOwner = this

        return binding.root
    }

    companion object {
        const val KEY = "KEY"
        fun newInstance(email: String) =
            WelcomeFragment().apply {
                arguments = Bundle().apply {
                    putString(KEY, email)
                }
            }
    }

}