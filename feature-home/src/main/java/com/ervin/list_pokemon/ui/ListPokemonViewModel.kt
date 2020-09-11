package com.ervin.list_pokemon.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.ervin.pokedex.core.data.source.Resource
import com.ervin.pokedex.core.domain.model.Pokemon
import com.ervin.pokedex.core.domain.usecase.home.HomeUseCase

class ListPokemonViewModel(private val homeUseCase: HomeUseCase) : ViewModel() {

    private val _pokemons: LiveData<Resource<List<Pokemon>>> =
        homeUseCase.getAllLocalPokemon().asLiveData()
    private val _elements: LiveData<Resource<Int>> =
        homeUseCase.maybeFetchRemoteElement().asLiveData()

    val pokemons = _pokemons
    val elements = _elements

    fun maybeFetchRemotePokemon() {
        homeUseCase.maybeFetchRemotePokemon()
    }
}