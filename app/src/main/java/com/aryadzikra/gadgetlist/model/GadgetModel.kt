package com.aryadzikra.gadgetlist.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GadgetModel (
    val name : String?,
    val description : String?,
    val price : String?,
    val rating : String?,
    val category : String?,
    val imageUrl : String?,
    val videoUrl : String?
) : Parcelable