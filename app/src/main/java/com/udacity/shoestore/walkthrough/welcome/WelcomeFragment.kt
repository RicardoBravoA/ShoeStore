package com.udacity.shoestore.walkthrough.welcome

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.udacity.shoestore.R
import com.udacity.shoestore.databinding.FragmentWelcomeBinding

class WelcomeFragment : Fragment() {

    private lateinit var viewModel: WelcomeViewModel
    private lateinit var viewModelFactory: WelcomeViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding: FragmentWelcomeBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_welcome,
            container,
            false
        )

        val welcomeFragmentArgs by navArgs<WelcomeFragmentArgs>()

        viewModelFactory = WelcomeViewModelFactory(welcomeFragmentArgs.email)
        viewModel = ViewModelProvider(this, viewModelFactory).get(WelcomeViewModel::class.java)

        binding.welcomeViewModel = viewModel
        binding.lifecycleOwner = this

        viewModel.navigation.observe(viewLifecycleOwner, { navigation ->
            navigation.getContentIfNotHandled()?.let {
                if (it) {
                    findNavController().navigate(
                        WelcomeFragmentDirections.actionWelcomeFragmentToInstructionFragment()
                    )
                }
            }
        })

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}