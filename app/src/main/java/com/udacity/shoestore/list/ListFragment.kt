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
import com.udacity.shoestore.utils.Constant

class ListFragment : Fragment() {

    private lateinit var viewModel: ListViewModel
    private lateinit var binding: FragmentListBinding
    private val listAdapter: ListAdapter by lazy { ListAdapter(requireActivity().contentResolver) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_list, container, false)
        viewModel = ViewModelProvider(this).get(ListViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

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

        viewModel.shoe.observe(viewLifecycleOwner, {
            it.getContentIfNotHandled()?.let { shoe ->
                listAdapter.addItem(shoe)
            }
        })

        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<ShoeModel>(Constant.KEY)
            ?.observe(viewLifecycleOwner) {
                if (it.name.isNotEmpty()) {
                    viewModel.addShoe(it)
                }
            }

        return binding.root
    }

}