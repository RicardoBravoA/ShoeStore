package com.udacity.shoestore.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.udacity.shoestore.R
import com.udacity.shoestore.databinding.FragmentListBinding
import com.udacity.shoestore.model.shoe.ShoeModel

class ListFragment : Fragment() {

    private lateinit var viewModel: ListViewModel
    private lateinit var binding: FragmentListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_list, container, false)
        viewModel = ViewModelProvider(this).get(ListViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        val listAdapter = ListAdapter(requireActivity().supportFragmentManager)

        binding.adapter = listAdapter

        viewModel.navigation.observe(viewLifecycleOwner, { navigation ->
            navigation.getContentIfNotHandled()?.let {
                if (it) {
                    findNavController().navigate(
                        ListFragmentDirections.actionListFragmentToDetailFragment()
                    )
                }
            }
        })

        viewModel.shoe.observe(viewLifecycleOwner, { data ->
            listAdapter.addItem(data.second, data.first)
        })

        val shoe = arguments?.getParcelable<ShoeModel>(KEY)

        shoe?.let {
            viewModel.addShoe(it)
        }

        return binding.root
    }

    companion object {
        private const val KEY = "shoe"
    }

}