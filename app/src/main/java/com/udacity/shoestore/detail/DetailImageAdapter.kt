package com.udacity.shoestore.detail

import android.content.ContentResolver
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.udacity.shoestore.R
import com.udacity.shoestore.databinding.ItemDetailImageAddBinding
import com.udacity.shoestore.databinding.ItemDetailImageBinding
import com.udacity.shoestore.model.detail.AddImageModel
import com.udacity.shoestore.model.detail.ImageModel
import com.udacity.shoestore.utils.ImageUtils

class DetailImageAdapter(
    private val addImageClick: () -> Unit,
    private val contentResolver: ContentResolver
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var data = listOf<Any>()

    enum class TYPE {
        ADD, IMAGE
    }

    override fun getItemViewType(position: Int): Int {
        return if (data[position] is AddImageModel) {
            TYPE.ADD.ordinal
        } else {
            TYPE.IMAGE.ordinal
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == TYPE.ADD.ordinal) {
            val binding: ItemDetailImageAddBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_detail_image_add,
                parent,
                false
            )
            return AddViewHolder(binding)
        } else {
            val binding: ItemDetailImageBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_detail_image,
                parent,
                false
            )
            return ImageViewHolder(binding)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == TYPE.ADD.ordinal) {
            holder as AddViewHolder
            holder.bind()
        } else {
            holder as ImageViewHolder
            holder.bind(data[position] as ImageModel)
        }
    }

    override fun getItemCount(): Int = data.size

    inner class AddViewHolder(binding: ItemDetailImageAddBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.addImageView.setOnClickListener {
                addImageClick()
            }
        }

        fun bind() {
            //Do nothing
        }
    }

    inner class ImageViewHolder(private val binding: ItemDetailImageBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(imageModel: ImageModel) {
            //Do nothing
            ImageUtils.loadImageFromUri(contentResolver, imageModel.image)?.let {
                binding.imageView.setImageBitmap(it)
            }
        }
    }

}