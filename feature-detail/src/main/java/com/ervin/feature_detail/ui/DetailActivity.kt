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
import kotlinx.android.synthetic.main.activity_detail.bg_detail
import kotlinx.android.synthetic.main.activity_detail.detail_toolbar
import kotlinx.android.synthetic.main.activity_detail.fab_bookmark
import kotlinx.android.synthetic.main.activity_detail.iv_poke_picture
import kotlinx.android.synthetic.main.activity_detail.poke_container
import kotlinx.android.synthetic.main.activity_detail.progressbar_attack
import kotlinx.android.synthetic.main.activity_detail.progressbar_defense
import kotlinx.android.synthetic.main.activity_detail.progressbar_hp
import kotlinx.android.synthetic.main.activity_detail.progressbar_sp_attack
import kotlinx.android.synthetic.main.activity_detail.progressbar_sp_defense
import kotlinx.android.synthetic.main.activity_detail.progressbar_speed
import kotlinx.android.synthetic.main.activity_detail.tv_height
import kotlinx.android.synthetic.main.activity_detail.tv_poke_attack
import kotlinx.android.synthetic.main.activity_detail.tv_poke_defense
import kotlinx.android.synthetic.main.activity_detail.tv_poke_hp
import kotlinx.android.synthetic.main.activity_detail.tv_poke_sp_attack
import kotlinx.android.synthetic.main.activity_detail.tv_poke_sp_defense
import kotlinx.android.synthetic.main.activity_detail.tv_poke_speed
import kotlinx.android.synthetic.main.activity_detail.tv_type_name1
import kotlinx.android.synthetic.main.activity_detail.tv_type_name2
import kotlinx.android.synthetic.main.activity_detail.tv_weight
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

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            /**
             * define toolbar
             */
            setSupportActionBar(detail_toolbar)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)

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
            detailViewModel.setFavoritePokemon(pokemon, true)
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

        val gd = GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, arrayColorTypes)
        poke_container.background = gd
        detail_toolbar.background = gd

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window: Window = window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = Color.parseColor(pokemon.listType[0].typeColor)
        }

        iv_poke_picture.loadImage(pokemon.pokemonSpritesUrl)
        tv_poke_attack.text = pokemon.pokemonAttack.toString()
        tv_poke_defense.text = pokemon.pokemonDefense.toString()
        tv_poke_hp.text = pokemon.pokemonHp.toString()
        tv_poke_speed.text = pokemon.pokemonSpeed.toString()
        tv_poke_sp_attack.text = pokemon.pokemonSpAtk.toString()
        tv_poke_sp_defense.text = pokemon.pokemonSpDef.toString()
        tv_weight.text = "${pokemon.pokemonWeight.toFloat() / 10} Kg"
        tv_height.text = "${pokemon.pokemonHeight.toFloat() / 10} m"

        progressbar_hp.progress = pokemon.pokemonHp
        progressbar_attack.progress = pokemon.pokemonAttack
        progressbar_defense.progress = pokemon.pokemonDefense
        progressbar_speed.progress = pokemon.pokemonSpeed
        progressbar_sp_attack.progress = pokemon.pokemonSpAtk
        progressbar_sp_defense.progress = pokemon.pokemonSpDef

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
        setBackgroundColor(Color.parseColor(type.typeColor))
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}