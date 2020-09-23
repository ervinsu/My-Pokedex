package com.ervin.pokedex.core.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * We need parcelable here, because we pass this class whenever we show the [com.ervin.feature_detail.ui.DetailActivity]
 */
@Parcelize
data class Pokemon(
    val pokemonId: Int,
    val pokemonName: String,
    val pokemonSpritesUrl: String,
    val pokemonSpeed: Int,
    val pokemonSpDef: Int,
    val pokemonSpAtk: Int,
    val pokemonDefense: Int,
    val pokemonAttack: Int,
    val pokemonHp: Int,
    val pokemonWeight: Int,
    val pokemonHeight: Int,
    val listType: List<Type>,
    var isFavorite: Boolean = false
) : Parcelable