package com.ervin.listpokemon.ui

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.ervin.library_common.extension.setGone
import com.ervin.library_common.extension.setVisible
import com.ervin.library_common.navigation.FeatureDetail
import com.ervin.library_common.util.calculateNoOfColumn
import com.ervin.listpokemon.R
import com.ervin.listpokemon.ui.adapter.ListPokemonAdapter
import com.ervin.pokedex.core.data.source.Resource
import kotlinx.android.synthetic.main.fragment_list_pokemon.*
import org.koin.android.scope.lifecycleScope
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class ListPokemonFragment : Fragment() {

    private val listPokemonViewModel: ListPokemonViewModel by viewModel()
    private val featureDetail: FeatureDetail by lifecycleScope.inject {
        parametersOf(this)
    }
    private val adapter: ListPokemonAdapter by lifecycleScope.inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_list_pokemon, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity == null) return
        initRecyclerview()

        listPokemonViewModel.pokemons.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Success -> {
                    pg_list_pokemon.setGone()
                    recyclerview_list_pokemon.setVisible()

                    if (it.data.isNullOrEmpty()) {
                        Toast.makeText(activity, "Data Not Found", Toast.LENGTH_SHORT).show()
                    } else {
                        adapter.setListPokemon(it.data!!)
                    }
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

        listPokemonViewModel.elements.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Success -> {
                    listPokemonViewModel.maybeFetchRemotePokemon()
                }
                is Resource.Loading -> {
                }
                is Resource.Error -> {
                    Toast.makeText(activity, "Failed to get Data Element", Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun initRecyclerview() {
        adapter.setListener(featureDetail)
        recyclerview_list_pokemon.adapter = adapter

        val rowSpan = calculateNoOfColumn(
            requireActivity().applicationContext,
            resources.getDimensionPixelSize(R.dimen.width_row) / resources.displayMetrics.scaledDensity
        )
        val layoutManager = GridLayoutManager(activity?.applicationContext, rowSpan)
        recyclerview_list_pokemon.layoutManager = layoutManager
    }
}