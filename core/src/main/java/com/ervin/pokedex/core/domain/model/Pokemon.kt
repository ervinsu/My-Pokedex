package com.ervin.pokedex.core.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Pokemon(
    val pokemonId: Int,
    val pokemonName: String
) : Parcelable