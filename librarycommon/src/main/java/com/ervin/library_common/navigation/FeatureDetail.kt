package com.ervin.library_common.navigation

import android.content.Intent

interface FeatureDetail {
    fun createIntent(): Intent

    companion object {
        const val POKEMON_EXTRA = "pokemon_extra"
    }
}