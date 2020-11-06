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

            when {
                it.itemCount <= 1 -> {
                    //Without margins
                }
                it.itemCount == 2 -> {
                    for (index in 0 until it.itemCount) {
                        when (index) {
                            0 -> outRect.right = space.toInt() / 2
                            1 -> outRect.left = space.toInt() / 2
                        }
                    }
                }
                else -> {
                    for (index in 0 until it.itemCount) {
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
    }

}