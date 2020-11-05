package com.udacity.shoestore.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.udacity.shoestore.R
import com.udacity.shoestore.databinding.ItemListBinding
import com.udacity.shoestore.models.ShoeModel
import com.udacity.shoestore.utils.diffUtil

class ListAdapter : RecyclerView.Adapter<ListAdapter.ListViewHolder>() {

    var data: List<ShoeModel> by diffUtil(
        emptyList(),
        areItemsTheSame = { old, new -> old.name == new.name }
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding: ItemListBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_list,
            parent,
            false
        )
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val shoe = data[position]
        holder.bind(shoe)
    }

    override fun getItemCount(): Int = data.size

    inner class ListViewHolder(private val binding: ItemListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(shoe: ShoeModel) {
            binding.titleText.text = shoe.name
            binding.companyText.text = shoe.company
            binding.sizeText.text = shoe.size.toString()
        }

    }

}