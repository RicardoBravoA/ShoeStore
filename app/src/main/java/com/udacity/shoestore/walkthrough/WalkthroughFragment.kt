package com.udacity.shoestore.walkthrough

import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.udacity.shoestore.R
import com.udacity.shoestore.databinding.FragmentWalkthroughBinding
import com.udacity.shoestore.common.ViewPagerAdapter
import com.udacity.shoestore.walkthrough.instruction.InstructionFragment
import com.udacity.shoestore.walkthrough.welcome.WelcomeFragment

class WalkthroughFragment : Fragment() {

    private lateinit var viewModel: WalkthroughViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentWalkthroughBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_walkthrough,
            container,
            false
        )

        val walkthroughFragmentArgs by navArgs<WalkthroughFragmentArgs>()

        val viewModelFactory = WalkthroughViewModelFactory(walkthroughFragmentArgs.email)
        viewModel = ViewModelProvider(this, viewModelFactory).get(WalkthroughViewModel::class.java)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        viewModel.showData.observe(viewLifecycleOwner, { showData ->
            if (showData) {
                val fragmentList = mutableListOf<Fragment>()
                fragmentList.add(WelcomeFragment.newInstance(walkthroughFragmentArgs.email))
                fragmentList.add(InstructionFragment())

                val pagerAdapter =
                    ViewPagerAdapter(requireActivity().supportFragmentManager, fragmentList)
                binding.walkthroughViewPager.adapter = pagerAdapter
                binding.indicatorView.attachToPager(binding.walkthroughViewPager)
            }
        })

        viewModel.navigation.observe(viewLifecycleOwner, { navigation ->
            navigation.getContentIfNotHandled()?.let {
                if (it) {
                    findNavController().navigate(
                        WalkthroughFragmentDirections.actionWalkthroughFragmentToListFragment()
                    )
                }
            }
        })

        return binding.root
    }

}