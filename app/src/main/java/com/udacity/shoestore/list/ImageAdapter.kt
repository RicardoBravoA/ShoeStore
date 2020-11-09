package com.udacity.shoestore.list

import android.content.ContentResolver
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.udacity.shoestore.R
import com.udacity.shoestore.databinding.ItemImageBinding
import com.udacity.shoestore.model.detail.ImageModel
import com.udacity.shoestore.utils.ImageUtils

class ImageAdapter(private val contentResolver: ContentResolver) :
    RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {

    var data: MutableList<ImageModel> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val binding: ItemImageBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_image,
            parent,
            false
        )
        return ImageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val image = data[position]
        holder.bind(image)
    }

    override fun getItemCount(): Int = data.size

    inner class ImageViewHolder(
        private val binding: ItemImageBinding
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(image: ImageModel) {
            image.image?.let {
                ImageUtils.loadImageFromUri(contentResolver, image.image!!)
                    ?.let { bitmap ->
                        binding.shoeImageView.setImageBitmap(bitmap)
                    }
            } ?: binding.shoeImageView.setImageResource(R.drawable.ic_no_photo)
        }

    }

}