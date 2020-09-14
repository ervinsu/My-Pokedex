package com.ervin.feature_detail.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ervin.pokedex.core.domain.model.Pokemon
import com.ervin.pokedex.core.domain.usecase.detail.DetailUseCase
import kotlinx.coroutines.launch

class DetailViewModel(private val detailUseCase: DetailUseCase) : ViewModel() {
    fun setFavoritePokemon(pokemon: Pokemon, isFavorite: Boolean) {
        viewModelScope.launch {
            detailUseCase.setFavoritePokemon(pokemon, isFavorite)
        }
    }
}