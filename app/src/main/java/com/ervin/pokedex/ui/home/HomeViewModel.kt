package com.ervin.pokedex.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.ervin.pokedex.core.data.source.Resource
import com.ervin.pokedex.core.domain.model.Pokemon
import com.ervin.pokedex.core.domain.usecase.home.HomeUseCase

class HomeViewModel(homeUseCase: HomeUseCase) : ViewModel() {

    private val _pokemons = homeUseCase.getAllPokemon().asLiveData()
    val pokemons: LiveData<Resource<List<Pokemon>>> = _pokemons
}