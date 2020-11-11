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
import com.udacity.shoestore.databinding.ItemListBinding
import com.udacity.shoestore.model.shoe.ShoeModel

class ListFragment : Fragment() {

    private lateinit var viewModel: ListViewModel
    private lateinit var binding: FragmentListBinding

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
            showItems(it, inflater)
        })

        sharedViewModel.shoe.observe(viewLifecycleOwner, {
            if (it.name.isNotEmpty()) {
                viewModel.addShoe(it)
                sharedViewModel.clearShoe()
            }
        })

        return binding.root
    }

    private fun showItems(shoeList: MutableList<ShoeModel>, inflater: LayoutInflater) {
        binding.shoeLinearLayout.removeAllViews()

        shoeList.forEach {
            val viewBinding: ItemListBinding =
                DataBindingUtil.inflate(
                    inflater, R.layout.item_list,
                    binding.shoeLinearLayout,
                    false
                )
            val imageAdapter: ImageAdapter by lazy { ImageAdapter(requireActivity().contentResolver) }

            viewBinding.titleText.text = it.name
            viewBinding.companyText.text = requireContext()
                .getString(R.string.list_company_label, it.company)
            viewBinding.sizeText.text =
                requireContext().getString(R.string.list_size_label, it.size.toString())
            viewBinding.descriptionText.text =
                requireContext().getString(R.string.list_description_label, it.description)

            val images = it.images

            imageAdapter.data = images.toMutableList()
            viewBinding.shoeRecyclerView.adapter = imageAdapter

            if (images.size > 1) {
                viewBinding.indicatorView.visibility = View.VISIBLE
                viewBinding.indicatorView.attachToRecyclerView(viewBinding.shoeRecyclerView)
            } else {
                viewBinding.indicatorView.visibility = View.GONE
            }
            binding.shoeLinearLayout.addView(viewBinding.root)
        }
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