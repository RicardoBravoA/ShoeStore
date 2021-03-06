package com.udacity.shoestore.list

import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.udacity.shoestore.R
import com.udacity.shoestore.common.SharedDetailListViewModel
import com.udacity.shoestore.databinding.FragmentListBinding

class ListFragment : Fragment() {

    private lateinit var viewModel: ListViewModel
    private lateinit var binding: FragmentListBinding
    private val listAdapter: ListAdapter by lazy { ListAdapter(requireActivity().contentResolver) }
    private val sharedViewModel: SharedDetailListViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_list, container, false)
        viewModel = ViewModelProvider(this).get(ListViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        setHasOptionsMenu(true)
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

        viewModel.shoeList.observe(viewLifecycleOwner, {
            listAdapter.data = it
        })

        sharedViewModel.shoe.observe(viewLifecycleOwner, {
            if (it.name.isNotEmpty()) {
                viewModel.addShoe(it)
                sharedViewModel.clearShoe()
            }
        })

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_logout, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.logout -> {
                findNavController().navigate(
                    ListFragmentDirections.actionListFragmentToLoginFragment()
                )
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}