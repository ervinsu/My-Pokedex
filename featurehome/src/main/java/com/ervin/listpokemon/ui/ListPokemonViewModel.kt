package com.ervin.listpokemon.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.ervin.pokedex.core.data.source.Resource
import com.ervin.pokedex.core.domain.model.Pokemon
import com.ervin.pokedex.core.domain.usecase.home.HomeUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.mapLatest

@FlowPreview
@ExperimentalCoroutinesApi
class ListPokemonViewModel(private val homeUseCase: HomeUseCase) : ViewModel() {

    private val _pokemons: LiveData<Resource<List<Pokemon>>> =
        homeUseCase.getAllLocalPokemon().asLiveData()
    private val _elements: LiveData<Resource<Int>> =
        homeUseCase.maybeFetchRemoteElement().asLiveData()
    val queryChannel = BroadcastChannel<String>(Channel.CONFLATED)

    val pokemons = _pokemons
    val elements = _elements
    val searchResult =
        queryChannel.asFlow()
            .debounce(300)
            .distinctUntilChanged()
            .mapLatest {
                homeUseCase.getSearchedPokemon(it)
            }
            .asLiveData()


    fun maybeFetchRemotePokemon() {
        homeUseCase.maybeFetchRemotePokemon()
    }
}