package com.udacity.shoestore.utils

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration

class RecyclerViewDecoration(private val space: Float) : ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        parent.adapter?.let {
            for (index in 0 until parent.adapter!!.itemCount) {
                when (index) {
                    0 -> outRect.left = 0
                    parent.adapter!!.itemCount - 1 -> outRect.right = 0
                    else -> {
                        outRect.left = space.toInt() / 2
                        outRect.right = space.toInt() / 2
                    }
                }
            }
        }
    }

}