package com.ervin.featurefavorite.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.ervin.feature_detail.ui.DetailActivity
import com.ervin.featurefavorite.R
import com.ervin.featurefavorite.di.favoritePokemonModule
import com.ervin.featurefavorite.ui.adapter.FavoritePokemonAdapter
import com.ervin.library_common.extension.setGone
import com.ervin.library_common.extension.setVisible
import com.ervin.library_common.navigation.FeatureDetail
import com.ervin.library_common.util.calculateNoOfColumn
import com.ervin.pokedex.core.data.source.Resource
import kotlinx.android.synthetic.main.fragment_favorite_pokemon.*
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.java.KoinJavaComponent.get

class FavoritePokemonFragment : Fragment() {

    private val favoritePokemonViewModel: FavoritePokemonViewModel by viewModel()
    private val adapter: FavoritePokemonAdapter by lazy {
        get(FavoritePokemonAdapter::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        loadKoinModules(favoritePokemonModule)
        return inflater.inflate(R.layout.fragment_favorite_pokemon, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity == null) return
        initRecyclerview()

        favoritePokemonViewModel.favoritePokemon.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Success -> {
                    pg_list_pokemon.setGone()
                    recyclerview_list_pokemon.setVisible()

                    if (it.data.isNullOrEmpty()) {
                        Toast.makeText(
                            activity,
                            "No favorite pokemon, let's have some!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    adapter.setListPokemon(it.data!!)
                }
                is Resource.Error -> {
                    recyclerview_list_pokemon.setGone()
                    pg_list_pokemon.setGone()
                    Toast.makeText(activity, "Failed to get Data Pokemon", Toast.LENGTH_LONG).show()
                }
                is Resource.Loading -> {
                    recyclerview_list_pokemon.setGone()
                    pg_list_pokemon.setVisible()
                }
            }
        })
    }

    private fun initRecyclerview() {
        adapter.setListener(object : FeatureDetail {
            override fun createIntent(): Intent {
                return Intent(activity, DetailActivity::class.java)
            }
        })
        recyclerview_list_pokemon.adapter = adapter

        val rowSpan = calculateNoOfColumn(
            requireActivity().applicationContext,
            resources.getDimensionPixelSize(R.dimen.width_row) / resources.displayMetrics.scaledDensity
        )
        val layoutManager = GridLayoutManager(activity?.applicationContext, rowSpan)
        recyclerview_list_pokemon.layoutManager = layoutManager
    }
}