package com.udacity.shoestore.detail

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.core.view.ViewCompat
import androidx.core.view.children
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.chip.Chip
import com.udacity.shoestore.R
import com.udacity.shoestore.common.SharedDetailListViewModel
import com.udacity.shoestore.databinding.FragmentDetailBinding
import com.udacity.shoestore.databinding.ItemChipSizeBinding
import com.udacity.shoestore.model.detail.ImageModel
import com.udacity.shoestore.model.shoe.ShoeModel
import com.udacity.shoestore.utils.*

class DetailFragment : Fragment() {

    private lateinit var viewModel: DetailViewModel
    private lateinit var binding: FragmentDetailBinding
    private var alertDialog: AlertDialog? = null
    private val sharedViewModel: SharedDetailListViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail, container, false)
        viewModel = ViewModelProvider(this).get(DetailViewModel::class.java)
        binding.lifecycleOwner = this

        setHasOptionsMenu(true)

        binding.imageRecyclerView.addItemDecoration(RecyclerViewDecoration(resources.getDimension(R.dimen.layout_padding)))
        val imageAdapter = DetailImageAdapter(::addImageClick, requireActivity().contentResolver)
        binding.adapter = imageAdapter

        viewModel.imageList.observe(viewLifecycleOwner, { list ->
            imageAdapter.data = list
            imageAdapter.notifyDataSetChanged()
        })

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

        binding.nameEdit.addTextChangedListener(CustomTextWatcher(
            onChanged = { _, _, _, _ ->
                binding.nameTextInputLayout.validateErrorInputLayout()
                viewModel.validateNameWatcher()
            }
        ))

        binding.descriptionEdit.addTextChangedListener(CustomTextWatcher(
            onChanged = { _, _, _, _ ->
                binding.descriptionTextInputLayout.validateErrorInputLayout()
                viewModel.validateDescriptionWatcher()
            }
        ))

        binding.companyEdit.addTextChangedListener(CustomTextWatcher(
            onChanged = { _, _, _, _ ->
                binding.companyTextInputLayout.validateErrorInputLayout()
                viewModel.validateCompanyWatcher()
            }
        ))

        viewModel.validateSize.observe(viewLifecycleOwner, { isValid ->
            if (!isValid) {
                showAlertSize()
            }
        })

        viewModel.shoe.observe(viewLifecycleOwner, {
            navigation(it)
        })

        viewModel.showChips.observe(viewLifecycleOwner, { show ->
            if (show) {
                showChips(inflater)
            }
        })

        binding.saveButton.setOnClickListener {
            viewModel.validateData(
                binding.nameEdit.text.toString(),
                binding.descriptionEdit.text.toString(),
                binding.companyEdit.text.toString(),
                sizeSelected(),
                viewModel.images()
            )

        }

        return binding.root
    }

    private fun navigation(shoe: ShoeModel) {
        sharedViewModel.addShoe(shoe)
        findNavController().popBackStack()
    }

    private fun showChips(inflater: LayoutInflater) {
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
        if (alertDialog == null) {
            val builder = AlertDialog.Builder(requireContext())
            builder
                .setMessage(getString(R.string.error_size))
                .setPositiveButton(android.R.string.ok) { dialog, _ ->
                    dialog.dismiss()
                    viewModel.completeSizeErrorMessage()
                }
            alertDialog = builder.create()
        }

        alertDialog?.let {
            if (!it.isShowing) {
                it.show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE) {
            data?.data?.let {
                viewModel.addImage(ImageModel(it))
            }
        }
    }

    private fun addImageClick() {
        val photoPickerIntent = Intent(Intent.ACTION_PICK)
        photoPickerIntent.type = TYPE
        startActivityForResult(photoPickerIntent, IMAGE)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_logout, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.logout -> {
                findNavController().navigate(
                    DetailFragmentDirections.actionDetailFragmentToLoginFragment()
                )
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    companion object {
        const val IMAGE = 100
        const val TYPE = "image/*"
    }

}