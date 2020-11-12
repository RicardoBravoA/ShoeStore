package com.udacity.shoestore.list

import android.content.ContentResolver
import android.content.Context
import android.view.LayoutInflater
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.udacity.shoestore.R
import com.udacity.shoestore.databinding.ItemListBinding
import com.udacity.shoestore.model.shoe.ShoeModel

class ListAdapter(private val contentResolver: ContentResolver) :
    RecyclerView.Adapter<ListAdapter.ListViewHolder>() {

    var data: MutableList<ShoeModel> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding: ItemListBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_list,
            parent,
            false
        )
        return ListViewHolder(binding, parent.context)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val shoe = data[position]
        holder.bind(shoe)
    }

    override fun getItemCount(): Int = data.size

    fun addItem(shoe: ShoeModel, position: Int = itemCount) {
        data.add(position, shoe)
        notifyItemInserted(position)
    }

    inner class ListViewHolder(private val binding: ItemListBinding, private val context: Context) :
        RecyclerView.ViewHolder(binding.root) {

        private val imageAdapter: ImageAdapter by lazy { ImageAdapter(contentResolver) }

        fun bind(shoe: ShoeModel) {
            binding.titleText.text = shoe.name
            binding.companyText.text = context.getString(R.string.list_company_label, shoe.company)
            binding.sizeText.text =
                context.getString(R.string.list_size_label, shoe.size.toString())

            val images = shoe.images

            imageAdapter.data = images.toMutableList()
            binding.shoeRecyclerView.adapter = imageAdapter

            if (images.size > 1) {
                binding.indicatorView.visibility = VISIBLE
                binding.indicatorView.attachToRecyclerView(binding.shoeRecyclerView)
            } else {
                binding.indicatorView.visibility = GONE
            }

        }

    }

}