package com.ervin.feature_home.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.ervin.pokedex.core.data.source.Resource
import com.ervin.pokedex.core.domain.model.Pokemon
import com.ervin.pokedex.core.domain.usecase.home.HomeUseCase

class HomeViewModel(private val homeUseCase: HomeUseCase) : ViewModel() {

    private val _pokemons = homeUseCase.getAllLocalPokemon().asLiveData()
    private val _elements = homeUseCase.maybeFetchRemoteElement().asLiveData()

    val pokemons: LiveData<Resource<List<Pokemon>>> = _pokemons
    val elements = _elements

    fun maybeFetchRemotePokemon() {
        homeUseCase.maybeFetchRemotePokemon()
    }
}