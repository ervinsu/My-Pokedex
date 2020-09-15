package com.ervin.featurefavorite.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.ervin.pokedex.core.domain.usecase.home.HomeUseCase

class FavoritePokemonViewModel(homeUseCase: HomeUseCase) : ViewModel() {

    private val _favoritePokemon = homeUseCase.getAllFavoritePokemon().asLiveData()

    val favoritePokemon = _favoritePokemon
}