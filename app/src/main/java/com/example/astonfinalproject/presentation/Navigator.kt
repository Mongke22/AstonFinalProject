package com.example.astonfinalproject.presentation

import com.example.astonfinalproject.R
import com.example.astonfinalproject.presentation.fragments.*
import com.example.astonfinalproject.presentation.viewModel.MainViewModel
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import javax.inject.Inject

class Navigator @Inject constructor() {
    var activity: MainActivity? = null

    fun moveToCharacterDetailScreen(viewModel: MainViewModel, characterId: Int){
        setupBackButton()
        setToolBarTitle("О персонаже")
        activity!!.supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentContainer, SingleCharacterFragment.newInstance(viewModel, characterId))
            .commit()
    }

    fun moveToEpisodeDetailScreen(viewModel: MainViewModel, episodeId: Int){
        setupBackButton()
        setToolBarTitle("Об эпизоде")
        activity!!.supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentContainer, SingleEpisodeFragment.newInstance(viewModel, episodeId))
            .commit()
    }

    fun moveToLocationDetailScreen(viewModel: MainViewModel, locationId: Int, place: String){
        setupBackButton()
        setToolBarTitle("О локации")
        if(place != ""){
            activity!!.supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragmentContainer, SingleLocationFragment.newInstance(viewModel, place))
                .commit()
        }
        else{
            activity!!.supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragmentContainer, SingleLocationFragment.newInstance(viewModel, locationId))
                .commit()
        }
    }

    fun moveToCharactersScreen(viewModel: MainViewModel){
        hideBackButton()
        displayCurrentMenuPositionOnCharacter()
        setToolBarTitle("Персонажи")
        activity!!.supportFragmentManager.popBackStack()
        activity!!.supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentContainer, CharacterFragment.newInstance(viewModel))
            .commit()
    }

    fun moveToEpisodesScreen(viewModel: MainViewModel){
        setupBackButton()
        setToolBarTitle("Эпизоды")
        activity!!.supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentContainer, EpisodeFragment.newInstance(viewModel))
            .commit()
    }

    fun moveToLocationsScreen(viewModel: MainViewModel){
        setupBackButton()
        setToolBarTitle("Локации")
        activity!!.supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentContainer, LocationFragment.newInstance(viewModel))
            .commit()
    }

    private fun setupBackButton(){
        activity!!.findViewById<MaterialToolbar>(R.id.toolBar).setNavigationIcon(R.drawable.ic_arrow_back)
    }

    private fun displayCurrentMenuPositionOnCharacter(){
        activity!!.findViewById<BottomNavigationView>(R.id.bottomNavigationView).menu
            .findItem(R.id.characters_fragment).isChecked = true
    }

    private fun hideBackButton(){
        activity!!.findViewById<MaterialToolbar>(R.id.toolBar).navigationIcon = null
    }

    private fun setToolBarTitle(title: String){
        activity!!.findViewById<MaterialToolbar>(R.id.toolBar).title = title
    }
}