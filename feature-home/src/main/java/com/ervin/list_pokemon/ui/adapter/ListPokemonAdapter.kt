package com.ervin.list_pokemon.ui.adapter

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.os.SystemClock
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.recyclerview.widget.RecyclerView
import com.ervin.library_common.base.BaseViewHolder
import com.ervin.library_common.extension.loadImage
import com.ervin.library_common.extension.onClick
import com.ervin.library_common.navigation.FeatureDetail
import com.ervin.list_pokemon.R
import com.ervin.pokedex.core.domain.model.Pokemon
import kotlinx.android.synthetic.main.home_view_item.view.iv_poke_picture
import kotlinx.android.synthetic.main.home_view_item.view.poke_container
import kotlinx.android.synthetic.main.home_view_item.view.poke_name

class ListPokemonAdapter :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var listPokemon = mutableListOf<Pokemon>()
    private lateinit var listener: FeatureDetail
    private var mLastClickTime: Long = 0

    fun setListener(featureDetail: FeatureDetail) {
        listener = featureDetail
    }

    fun setListPokemon(newList: List<Pokemon>) {
        listPokemon.clear()
        listPokemon.addAll(newList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ListPokemonViewHolder(parent).onClick { view, position ->
            val activity = view.context as? Activity ?: return@onClick

            /**
             * make sure it can't be doubled click
             */
            if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                return@onClick
            }
            mLastClickTime = SystemClock.elapsedRealtime()

            /**
             * create the transition option
             */
            val pPokePicture = Pair.create<View, String>(view.iv_poke_picture, "pokePicture")
            val pPokeContainer = Pair.create(view.poke_container as View, "pokeContainer")
            val pBgDetail = Pair.create(view.poke_container as View, "bgDetail")
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                activity,
                pPokeContainer,
                pPokePicture,
                pBgDetail
            )

            val intent = listener.createIntent().apply {
                putExtra(FeatureDetail.POKEMON_EXTRA, listPokemon[position])
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                activity.startActivity(intent, options.toBundle())
            } else {
                activity.startActivity(intent)
            }
        }
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