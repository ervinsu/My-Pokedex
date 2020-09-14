package com.ervin.list_pokemon.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.ervin.library_common.navigation.FeatureDetail
import com.ervin.library_common.util.calculateNoOfColumn
import com.ervin.list_pokemon.R
import com.ervin.list_pokemon.ui.adapter.ListPokemonAdapter
import com.ervin.pokedex.core.data.source.Resource
import kotlinx.android.synthetic.main.fragment_home.view.recyclerview_list_pokemon
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
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity == null) return
        view.initRecyclerview()

        listPokemonViewModel.pokemons.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Success -> {
                    if (it.data.isNullOrEmpty()) {
                        Toast.makeText(activity, "Data Not Found", Toast.LENGTH_SHORT).show()
                    } else {
                        adapter.setListPokemon(it.data!!)
                    }
                    Log.d("HMM", "success ${it.data?.size}")
                }
                is Resource.Error -> {
                    Log.d("HMM", "${it.message}")
                }
                is Resource.Loading -> {
                    Log.d("HMM", "loading")
                }
            }
        })

        listPokemonViewModel.elements.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Success -> {
                    Toast.makeText(
                        activity,
                        "Data Element pokemon already up-to date",
                        Toast.LENGTH_SHORT
                    ).show()
                    listPokemonViewModel.maybeFetchRemotePokemon()
                    Log.d("HMM", "Type-succes ${it.data}")
                }
                is Resource.Loading -> {
                    Log.d("HMM", "Type-loading")
                }
                is Resource.Error -> {
                    Log.d("HMM", "Type-Error")
                }
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun View.initRecyclerview() {
        adapter.setListener(featureDetail)
        this.recyclerview_list_pokemon.adapter = adapter

        val rowSpan = calculateNoOfColumn(
            requireActivity().applicationContext,
            resources.getDimensionPixelSize(R.dimen.width_row) / resources.displayMetrics.scaledDensity
        )
        val layoutManager = GridLayoutManager(activity?.applicationContext, rowSpan)
        this.recyclerview_list_pokemon.layoutManager = layoutManager
    }
}