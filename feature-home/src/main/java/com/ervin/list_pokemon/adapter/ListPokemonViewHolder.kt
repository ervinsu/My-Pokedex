package com.ervin.list_pokemon.adapter

import android.view.View
import com.ervin.library_common.base.BaseViewHolder
import com.ervin.pokedex.core.domain.model.Pokemon
import kotlinx.android.synthetic.main.home_view_item.view.*


class ListPokemonViewHolder(view: View) : BaseViewHolder(view) {
    fun bind(pokemon: Pokemon) {
        itemView.pokeName.text = pokemon.pokemonName
    }
}