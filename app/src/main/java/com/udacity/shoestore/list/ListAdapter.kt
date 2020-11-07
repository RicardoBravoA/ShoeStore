package com.udacity.shoestore.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.udacity.shoestore.R
import com.udacity.shoestore.common.ViewPagerAdapter
import com.udacity.shoestore.databinding.ItemListBinding
import com.udacity.shoestore.model.shoe.ShoeModel
import com.udacity.shoestore.utils.diffUtil
import com.udacity.shoestore.utils.init

class ListAdapter(private val fragmentManager: FragmentManager) :
    RecyclerView.Adapter<ListAdapter.ListViewHolder>() {

    var data: MutableList<ShoeModel> by diffUtil(
        mutableListOf(),
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

    fun addItem(shoe: ShoeModel, position: Int) {
        data.add(position, shoe)
        notifyItemInserted(position)
    }

    inner class ListViewHolder(private val binding: ItemListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(shoe: ShoeModel) {
            binding.titleText.text = shoe.name
            binding.companyText.text = shoe.company
            binding.sizeText.text = shoe.size.toString()

            val fragmentList = arrayListOf<Fragment>()
            val images = shoe.images
            if (images.isEmpty()) {
                binding.indicatorView.visibility = View.GONE
                val fragment = ImageFragment.newInstance(null)
                fragmentList.add(fragment)
            } else {
                binding.indicatorView.visibility = View.VISIBLE
                images.forEach {
                    val fragment = ImageFragment.newInstance(it)
                    fragmentList.add(fragment)
                }
            }
            val pagerAdapter =
                ViewPagerAdapter(fragmentManager, fragmentList)
            binding.imageViewPager.adapter = pagerAdapter
            binding.indicatorView.init(binding.imageViewPager)
        }

    }

}