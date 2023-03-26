package com.example.astonfinalproject.presentation

import androidx.lifecycle.ViewModel
import com.example.astonfinalproject.R
import com.example.astonfinalproject.presentation.fragments.*
import com.google.android.material.appbar.MaterialToolbar

class Navigator {
    var activity: MainActivity? = null

    fun moveToCharacterDetailScreen(viewModel: MainViewModel, characterId: Int){
        setupBackButton()
        activity!!.supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentContainer, SingleCharacterFragment.newInstance(viewModel, characterId))
            .commit()
    }

    fun moveToEpisodeDetailScreen(viewModel: MainViewModel, episodeId: Int){
        setupBackButton()
        activity!!.supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentContainer, SingleEpisodeFragment.newInstance(viewModel, episodeId))
            .commit()
    }

    fun moveToLocationDetailScreen(viewModel: MainViewModel, locationId: Int){
        setupBackButton()
        activity!!.supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentContainer, SingleLocationFragment.newInstance(viewModel, locationId))
            .commit()
    }

    fun moveToCharactersScreen(viewModel: MainViewModel){
        hideBackButton()
        activity!!.supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentContainer, CharacterFragment.newInstance(viewModel))
            .commit()
    }

    fun moveToEpisodesScreen(viewModel: MainViewModel){
        setupBackButton()
        activity!!.supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentContainer, EpisodeFragment.newInstance(viewModel))
            .commit()
    }

    fun moveToLocationsScreen(viewModel: MainViewModel){
        setupBackButton()
        activity!!.supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentContainer, LocationFragment.newInstance(viewModel))
            .commit()
    }

    private fun setupBackButton(){
        activity!!.findViewById<MaterialToolbar>(R.id.toolBar).setNavigationIcon(R.drawable.ic_arrow_back)
    }

    private fun hideBackButton(){
        activity!!.findViewById<MaterialToolbar>(R.id.toolBar).navigationIcon = null
    }
}