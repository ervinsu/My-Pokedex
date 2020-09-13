package com.ervin.pokedex.core.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Type(
    val typeId: Int,
    val typeName: String,
    val typeColor: String
) : Parcelable