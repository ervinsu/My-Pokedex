package com.ervin.list_pokemon.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ervin.list_pokemon.R
import com.ervin.pokedex.core.domain.model.Pokemon

class ListPokemonAdapter : PagedListAdapter<Pokemon, RecyclerView.ViewHolder>(
    DATA_COMPARATOR
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ListPokemonViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.home_view_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val pokemon = getItem(position) ?: return
        (holder as ListPokemonViewHolder).bind(pokemon)
    }

    companion object {
        private val DATA_COMPARATOR = object : DiffUtil.ItemCallback<Pokemon>() {
            override fun areItemsTheSame(oldItem: Pokemon, newItem: Pokemon): Boolean =
                oldItem == newItem

            override fun areContentsTheSame(oldItem: Pokemon, newItem: Pokemon): Boolean =
                oldItem.pokemonId == newItem.pokemonId
        }
    }
}