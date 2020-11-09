package com.udacity.shoestore.model.shoe

import android.os.Parcelable
import com.udacity.shoestore.model.detail.ImageModel
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ShoeModel(
    val name: String, val description: String, val company: String, val size: Double,
    val images: List<ImageModel>
) : Parcelable