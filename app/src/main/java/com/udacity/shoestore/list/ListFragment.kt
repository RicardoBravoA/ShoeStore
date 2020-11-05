package com.udacity.shoestore.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.udacity.shoestore.R
import com.udacity.shoestore.databinding.FragmentListBinding
import com.udacity.shoestore.model.ShoeModel

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

        val listAdapter = ListAdapter()
        viewModel.addShoeList(loadData())

        viewModel.shoeList.observe(viewLifecycleOwner, { data ->
            listAdapter.data = data
        })

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

        return binding.root
    }

    private fun loadData(): MutableList<ShoeModel> {
        val list = mutableListOf<ShoeModel>()

        for (i in 1..5) {
            val shoe =
                ShoeModel("Name $i", i.toDouble(), "Company $i", "Description $i", arrayListOf())
            list.add(shoe)
        }
        return list
    }

}