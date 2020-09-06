package com.ervin.pokedex.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.ervin.pokedex.R
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
                    Toast.makeText(activity, "${it.data!!.size}", Toast.LENGTH_LONG).show()
                    Log.d("HMM", "success")
                }
                is Resource.Error -> {
                    Log.d("HMM", "error")
                }
                is Resource.Loading -> {
                    Log.d("HMM", "loading")
                }
            }
        })

        return inflater.inflate(R.layout.fragment_home, container, false)
    }
}