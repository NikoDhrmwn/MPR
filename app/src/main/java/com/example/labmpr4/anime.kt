package com.example.labmpr4

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class anime(
    var id: Int?,
    var name: String?,
    var img: String?

    // var nama: String,
    // var description: String,
    // var photo: Int
) : Parcelable
