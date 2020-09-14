package com.ervin.feature_detail.ui

import android.os.Build
import android.os.Bundle
import android.transition.Fade
import androidx.appcompat.app.AppCompatActivity
import com.ervin.feature_detail.R
import com.ervin.library_common.extension.loadImage
import com.ervin.library_common.navigation.FeatureDetail
import com.ervin.pokedex.core.domain.model.Pokemon
import kotlinx.android.synthetic.main.activity_detail.bg_detail
import kotlinx.android.synthetic.main.activity_detail.detail_toolbar
import kotlinx.android.synthetic.main.activity_detail.iv_poke_picture
import kotlinx.android.synthetic.main.activity_detail.poke_container

class DetailActivity : AppCompatActivity() {

    private lateinit var pokemon: Pokemon

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        pokemon = intent.getParcelableExtra(FeatureDetail.POKEMON_EXTRA)!!

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            /**
             * define toolbar
             */
            detail_toolbar.title = pokemon.pokemonName
            setSupportActionBar(detail_toolbar)

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
        iv_poke_picture.loadImage(pokemon.pokemonSpritesUrl)

    }
}