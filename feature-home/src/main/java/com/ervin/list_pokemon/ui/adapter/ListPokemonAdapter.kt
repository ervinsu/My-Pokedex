package com.ervin.list_pokemon.ui.adapter

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ervin.library_common.base.BaseViewHolder
import com.ervin.library_common.extension.loadImage
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
        return ListPokemonViewHolder(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val pokemon = listPokemon[position]
        (holder as ListPokemonViewHolder).bind(pokemon)
    }

    override fun getItemCount(): Int = listPokemon.size

    class ListPokemonViewHolder(parent: ViewGroup) : BaseViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.home_view_item, parent, false)
    ) {
        fun bind(pokemon: Pokemon) {
            itemView.poke_name.text = pokemon.pokemonName
            itemView.iv_poke_picture.loadImage(pokemon.pokemonSpritesUrl)
            val arrayColorTypes = IntArray(2)
            pokemon.listType.mapIndexed { position, type ->
                arrayColorTypes[position] =
                    Color.parseColor(type.typeColor)
                if (pokemon.listType.size == 1) {
                    arrayColorTypes[1] =
                        Color.parseColor(pokemon.listType[0].typeColor)
                }
            }

            val gd = GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, arrayColorTypes)
            gd.cornerRadius = 0f
            itemView.poke_container.background = gd
        }
    }
}