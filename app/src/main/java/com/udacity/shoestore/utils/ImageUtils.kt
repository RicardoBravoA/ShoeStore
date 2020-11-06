package com.udacity.shoestore.utils

import android.content.ContentResolver
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore

object ImageUtils {

    fun loadImageFromUri(contentResolver: ContentResolver, uri: Uri): Bitmap? {
        try {
            return if (Build.VERSION.SDK_INT < 28) {
                MediaStore.Images.Media.getBitmap(
                    contentResolver,
                    uri
                )
            } else {
                val source =
                    ImageDecoder.createSource(
                        contentResolver,
                        uri
                    )
                ImageDecoder.decodeBitmap(source)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

}