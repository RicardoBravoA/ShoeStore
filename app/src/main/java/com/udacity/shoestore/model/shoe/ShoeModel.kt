package com.udacity.shoestore.model.shoe

import android.net.Uri
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ShoeModel(
    var name: String, var description: String, var company: String, var size: Double,
    val images: List<Uri> = mutableListOf()
) : Parcelable