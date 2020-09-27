package com.ervin.feature_detail.ui

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.os.Bundle
import android.transition.Fade
import android.view.MenuItem
import android.view.Window
import android.view.WindowManager
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.ervin.feature_detail.R
import com.ervin.library_common.extension.loadImage
import com.ervin.library_common.navigation.FeatureDetail
import com.ervin.pokedex.core.domain.model.Pokemon
import com.ervin.pokedex.core.domain.model.Type
import kotlinx.android.synthetic.main.activity_detail.*
import org.koin.android.viewmodel.ext.android.viewModel

class DetailActivity : AppCompatActivity() {

    private val detailViewModel: DetailViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        val pokemon = intent.getParcelableExtra<Pokemon?>(FeatureDetail.POKEMON_EXTRA)

        pokemon?.let {
            initiateOtherAttribute(pokemon)
            initiateClickListener(pokemon)
            detail_toolbar.title = pokemon.pokemonName
        }

        /**
         * define toolbar
         */
        setSupportActionBar(detail_toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            /**
             * define transition animation
             */
            supportStartPostponedEnterTransition()
            val fade = Fade()
            fade.excludeTarget(iv_poke_picture, true)
            fade.excludeTarget(poke_container, true)
            fade.excludeTarget(bg_detail, true)
            window.enterTransition = fade
            window.exitTransition = fade
        }
    }

    private fun initiateClickListener(pokemon: Pokemon) {
        fab_bookmark.setOnClickListener {
            pokemon.isFavorite = !pokemon.isFavorite
            setFavoritePokemon(pokemon.isFavorite)
            detailViewModel.setFavoritePokemon(pokemon)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun initiateOtherAttribute(pokemon: Pokemon) {
        tv_type_name1.setType(pokemon.listType[0])

        val arrayColorTypes = IntArray(2)
        pokemon.listType.mapIndexed { position, type ->
            arrayColorTypes[position] =
                Color.parseColor(type.typeColor)
            if (pokemon.listType.size > 1) {
                tv_type_name2.setType(pokemon.listType[1])
            } else {
                arrayColorTypes[1] =
                    Color.parseColor(pokemon.listType[0].typeColor)
            }
        }

        val gd = GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, arrayColorTypes)
        poke_container.background = gd
        detail_toolbar.background = gd

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window: Window = window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = Color.parseColor(pokemon.listType[0].typeColor)
        }

        iv_poke_picture.loadImage(pokemon.pokemonSpritesUrl)
        progressViewAtk.labelText = pokemon.pokemonAttack.toString()
        progressViewDef.labelText = pokemon.pokemonDefense.toString()
        progressViewHp.labelText = pokemon.pokemonHp.toString()
        progressViewSpeed.labelText = pokemon.pokemonSpeed.toString()
        progressViewSpAtk.labelText = pokemon.pokemonSpAtk.toString()
        progressViewSpDef.labelText = pokemon.pokemonSpDef.toString()
        tv_weight.text = "${pokemon.pokemonWeight.toFloat() / 10} Kg"
        tv_height.text = "${pokemon.pokemonHeight.toFloat() / 10} m"

        progressViewHp.progress = pokemon.pokemonHp.toFloat()
        progressViewAtk.progress = pokemon.pokemonAttack.toFloat()
        progressViewDef.progress = pokemon.pokemonDefense.toFloat()
        progressViewSpeed.progress = pokemon.pokemonSpeed.toFloat()
        progressViewSpAtk.progress = pokemon.pokemonSpAtk.toFloat()
        progressViewSpDef.progress = pokemon.pokemonSpDef.toFloat()

        setFavoritePokemon(pokemon.isFavorite)
    }

    private fun setFavoritePokemon(isFavorite: Boolean) {
        if (isFavorite) {
            fab_bookmark.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_bookmarked))
        } else {
            fab_bookmark.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_bookmark))
        }
    }

    private fun TextView.setType(type: Type) {
        text = type.typeName
        val tempBackground = background as GradientDrawable
        tempBackground.setColor(Color.parseColor(type.typeColor))
        background = tempBackground
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}