package com.udacity.shoestore.detail

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.core.view.children
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.chip.Chip
import com.udacity.shoestore.R
import com.udacity.shoestore.databinding.FragmentDetailBinding
import com.udacity.shoestore.databinding.ItemChipSizeBinding
import com.udacity.shoestore.utils.showErrorMessageInputLayout

class DetailFragment : Fragment() {

    private lateinit var viewModel: DetailViewModel
    private lateinit var binding: FragmentDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail, container, false)
        viewModel = ViewModelProvider(this).get(DetailViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        addChips(inflater)

        viewModel.validateName.observe(viewLifecycleOwner, { isValid ->
            if (!isValid) {
                binding.nameTextInputLayout.showErrorMessageInputLayout(
                    getString(R.string.error_name)
                )
            }
        })

        viewModel.validateDescription.observe(viewLifecycleOwner, { isValid ->
            if (!isValid) {
                binding.descriptionTextInputLayout.showErrorMessageInputLayout(
                    getString(R.string.error_description)
                )
            }
        })

        viewModel.validateCompany.observe(viewLifecycleOwner, { isValid ->
            if (!isValid) {
                binding.companyTextInputLayout.showErrorMessageInputLayout(
                    getString(R.string.error_company)
                )
            }
        })

        viewModel.validateSize.observe(viewLifecycleOwner, { isValid ->
            if (!isValid) {
                showAlertSize()
            }
        })

        viewModel.shoe.observe(viewLifecycleOwner, { shoe ->
            shoe.getContentIfNotHandled()?.let {
                findNavController().navigate(
                    DetailFragmentDirections.actionDetailFragmentToListFragment(it)
                )
            }
        })

        binding.saveButton.setOnClickListener {
            viewModel.validateData(
                binding.nameEdit.text.toString(),
                binding.descriptionEdit.text.toString(),
                binding.companyEdit.text.toString(),
                sizeSelected()
            )
        }

        return binding.root
    }

    private fun addChips(inflater: LayoutInflater) {
        Log.i("z- addChips", "true")
        val chips = requireContext().resources.getStringArray(R.array.size)
        chips.forEach {
            val bindingChip: ItemChipSizeBinding =
                DataBindingUtil.inflate(
                    inflater,
                    R.layout.item_chip_size,
                    binding.sizeChipGroup,
                    false
                )
            bindingChip.sizeChip.id = ViewCompat.generateViewId()
            bindingChip.sizeChip.text = it
            bindingChip.lifecycleOwner = this
            binding.sizeChipGroup.addView(bindingChip.sizeChip)
        }
    }

    private fun sizeSelected(): String? {
        return binding.sizeChipGroup.children
            .filter { (it as Chip).isChecked }
            .map { (it as Chip).text.toString() }
            .firstOrNull()
    }

    private fun showAlertSize() {
        val builder = AlertDialog.Builder(requireContext())
        builder
            .setMessage(getString(R.string.error_size))
            .setPositiveButton(android.R.string.ok) { dialog, _ ->
                dialog.dismiss()
            }
        val alert = builder.create()
        alert.show()
    }

}