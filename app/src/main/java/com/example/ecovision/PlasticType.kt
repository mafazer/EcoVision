package com.example.ecovision

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PlasticType(
    val name: String,
    val imageResId: Int,  // Resource ID untuk gambar
    val imageIcon: Int,  // Resource ID untuk ikon recycle type
    val recyclingCode: String,
    val description: String,
    val recyclingProcess: String,
    val environmentalImpact: String,
    val examples: List<Int>  // Resource ID untuk contoh gambar
) : Parcelable
