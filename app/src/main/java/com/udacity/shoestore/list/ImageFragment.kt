package com.udacity.shoestore.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.udacity.shoestore.R
import com.udacity.shoestore.databinding.FragmentImageBinding
import com.udacity.shoestore.model.detail.ImageModel
import com.udacity.shoestore.utils.ImageUtils

class ImageFragment : Fragment() {

    private var imageModel: ImageModel? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentImageBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_image,
            container,
            false
        )

        arguments?.let {
            imageModel = it.getParcelable(KEY)
        }

        imageModel?.let {
            ImageUtils.loadImageFromUri(requireActivity().contentResolver, it.image)
                ?.let { bitmap ->
                    binding.shoeImageView.setImageBitmap(bitmap)
                }
        } ?: binding.shoeImageView.setImageResource(R.drawable.ic_no_photo)

        return binding.root
    }

    companion object {
        const val KEY = "KEY"

        fun newInstance(imageModel: ImageModel?) =
            ImageFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(KEY, imageModel)
                }
            }
    }

}