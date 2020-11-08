package com.udacity.shoestore.list

import android.content.Context
import android.view.LayoutInflater
import android.view.View.GONE
import android.view.View.VISIBLE
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
        return ListViewHolder(binding, parent.context)
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

    inner class ListViewHolder(private val binding: ItemListBinding, private val context: Context) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(shoe: ShoeModel) {
            binding.titleText.text = shoe.name
            binding.companyText.text = context.getString(R.string.list_company_label, shoe.company)
            binding.sizeText.text =
                context.getString(R.string.list_size_label, shoe.size.toString())

            val fragmentList = arrayListOf<Fragment>()
            val images = shoe.images
            if (images.isEmpty()) {
                val fragment = ImageFragment.newInstance(null)
                fragmentList.add(fragment)
            } else {
                binding.indicatorView.visibility = VISIBLE
                images.forEach {
                    val fragment = ImageFragment.newInstance(it)
                    fragmentList.add(fragment)
                }

            }

            if (images.size > 1) {
                binding.indicatorView.visibility = VISIBLE
            } else {
                binding.indicatorView.visibility = GONE
            }

            val pagerAdapter =
                ViewPagerAdapter(fragmentManager, fragmentList)
            binding.imageViewPager.adapter = pagerAdapter
            binding.indicatorView.init(binding.imageViewPager)
        }

    }

}