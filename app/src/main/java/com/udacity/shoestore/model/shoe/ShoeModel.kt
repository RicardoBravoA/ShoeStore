package com.udacity.shoestore.model.shoe

import android.os.Parcelable
import com.udacity.shoestore.model.detail.ImageModel
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ShoeModel(
    var name: String, var description: String, var company: String, var size: Double,
    val images: List<ImageModel> = mutableListOf()
) : Parcelable