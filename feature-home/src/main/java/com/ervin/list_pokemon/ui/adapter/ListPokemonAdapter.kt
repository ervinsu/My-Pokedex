package com.ervin.list_pokemon.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ervin.library_common.base.BaseViewHolder
import com.ervin.list_pokemon.R
import com.ervin.pokedex.core.domain.model.Pokemon
import kotlinx.android.synthetic.main.home_view_item.view.*

class ListPokemonAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var listPokemon = mutableListOf<Pokemon>()

    fun setListPokemon(newList: List<Pokemon>) {
        listPokemon.clear()
        listPokemon.addAll(newList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ListPokemonViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.home_view_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val pokemon = listPokemon[position]
        (holder as ListPokemonViewHolder).bind(pokemon)
    }

    override fun getItemCount(): Int = listPokemon.size

    class ListPokemonViewHolder(view: View) : BaseViewHolder(view) {
        fun bind(pokemon: Pokemon) {
            itemView.pokeName.text = pokemon.pokemonName
        }
    }
}