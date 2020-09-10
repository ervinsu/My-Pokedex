package com.ervin.feature_home.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.ervin.feature_home.R
import com.ervin.pokedex.core.data.source.Resource
import org.koin.android.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {

    private val homeViewModel: HomeViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel.pokemons.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Success -> {
                    Toast.makeText(activity, "${it.data?.size}", Toast.LENGTH_LONG).show()
                    Log.d("HMM", "success ${it.data?.size}")
                }
                is Resource.Error -> {
                    Log.d("HMM", "error")
                }
                is Resource.Loading -> {
                    Log.d("HMM", "loading")
                }
            }
        })

        homeViewModel.elements.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Success -> {
                    Toast.makeText(
                        activity,
                        "Data Element pokemon already up-to date",
                        Toast.LENGTH_SHORT
                    ).show()
                    homeViewModel.maybeFetchRemotePokemon()
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

        return inflater.inflate(R.layout.fragment_home, container, false)
    }
}