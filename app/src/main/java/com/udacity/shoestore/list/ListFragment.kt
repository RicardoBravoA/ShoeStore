package com.udacity.shoestore.list

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
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

        val shoe = arguments?.getParcelable<ShoeModel>("shoe")

        shoe?.let {
            Log.i("z- shoe", shoe.toString())
        }


        return binding.root
    }

    private fun loadData(): MutableList<ShoeModel> {
        val list = mutableListOf<ShoeModel>()

        for (i in 1..5) {
            val shoe =
                ShoeModel("Name $i", "Description $i", "Company $i", i.toDouble(), arrayListOf())
            list.add(shoe)
        }
        return list
    }

}