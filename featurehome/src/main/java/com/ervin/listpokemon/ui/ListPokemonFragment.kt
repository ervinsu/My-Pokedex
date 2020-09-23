package com.ervin.listpokemon.ui

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.ervin.library_common.extension.setGone
import com.ervin.library_common.extension.setVisible
import com.ervin.library_common.navigation.FeatureDetail
import com.ervin.library_common.util.calculateNoOfColumn
import com.ervin.library_common.util.hideKeyboard
import com.ervin.library_common.util.isServiceRunning
import com.ervin.listpokemon.R
import com.ervin.listpokemon.ui.adapter.ListPokemonAdapter
import com.ervin.pokedex.core.data.source.Resource
import com.ervin.pokedex.core.domain.backgroundservice.HomeFirstLaunchService
import kotlinx.android.synthetic.main.fragment_list_pokemon.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.launch
import org.koin.android.scope.ScopeFragment
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf


@ExperimentalCoroutinesApi
@FlowPreview
class ListPokemonFragment : ScopeFragment() {

    private val listPokemonViewModel: ListPokemonViewModel by viewModel()
    private val featureDetail: FeatureDetail by inject {
        parametersOf(this)
    }
    private val adapter: ListPokemonAdapter by inject()

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
        if (activity == null || context == null) return
        initRecyclerview()

        if (context?.isServiceRunning(HomeFirstLaunchService::class.java) != true) {
            listPokemonViewModel.pokemons.observe(viewLifecycleOwner, {
                when (it) {
                    is Resource.Success -> {
                        /**
                         * data not found and not trying to run service that fetch pokemons
                         */
                        if (it.data.isNullOrEmpty() && (context?.isServiceRunning(
                                HomeFirstLaunchService::class.java
                            ) == true)
                        ) {
                            Toast.makeText(activity, "Data Not Found", Toast.LENGTH_SHORT)
                                .show()
                        } else {
                            if (it.data.isNullOrEmpty()) {
                                Toast.makeText(
                                    activity,
                                    "Data Not Found, now fetching the pokemon",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else {
                                pg_list_pokemon.setGone()
                                recyclerview_list_pokemon.setVisible()
                                adapter.setListPokemon(it.data ?: listOf())
                            }
                        }
                    }
                    is Resource.Error -> {
                        recyclerview_list_pokemon.setGone()
                        pg_list_pokemon.setGone()
                        Toast.makeText(activity, "Failed to get Data Pokemon", Toast.LENGTH_LONG)
                            .show()
                    }
                    is Resource.Loading -> {
                        recyclerview_list_pokemon.setGone()
                        pg_list_pokemon.setVisible()
                    }
                }
            })

            listPokemonViewModel.elements.observe(viewLifecycleOwner, {
                when (it) {
                    is Resource.Success -> {
                        listPokemonViewModel.maybeFetchRemotePokemon()
                    }
                    is Resource.Loading -> {
                    }
                    is Resource.Error -> {
                        Toast.makeText(activity, "Failed to get Data Element", Toast.LENGTH_LONG)
                            .show()
                    }
                }
            })

            listPokemonViewModel.searchResult.observe(viewLifecycleOwner, {
                adapter.setListPokemon(it)
            })
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main, menu)
        val manager =
            requireActivity().getSystemService(Context.SEARCH_SERVICE) as SearchManager

        val search: SearchView = menu.findItem(R.id.app_bar_search)?.actionView as SearchView

        search.setSearchableInfo(manager.getSearchableInfo(requireActivity().componentName))

        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                activity.hideKeyboard()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                lifecycleScope.launch {
                    listPokemonViewModel.queryChannel.send(newText.toString())
                }
                return true
            }
        })
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