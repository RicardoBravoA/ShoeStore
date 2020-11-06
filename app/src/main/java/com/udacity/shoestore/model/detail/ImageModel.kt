package com.udacity.shoestore.model.detail

import android.net.Uri
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ImageModel(var image: Uri) : Parcelable