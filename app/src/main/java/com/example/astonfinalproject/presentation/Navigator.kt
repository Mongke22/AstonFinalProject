package com.example.astonfinalproject.presentation

import androidx.fragment.app.FragmentManager.POP_BACK_STACK_INCLUSIVE
import com.example.astonfinalproject.R
import com.example.astonfinalproject.presentation.fragments.*
import com.example.astonfinalproject.presentation.viewModel.MainViewModel
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import javax.inject.Inject

class Navigator @Inject constructor() {
    var activity: MainActivity? = null

    var localBackStack = ArrayList<String>()

    init{
        localBackStack.add("characters")
    }

    fun moveToCharacterDetailScreen(viewModel: MainViewModel, characterId: Int){
        setupBackButton()
        setToolBarTitle("О персонаже")
        activity!!.supportFragmentManager.popBackStack("characterDetail", POP_BACK_STACK_INCLUSIVE)
        popLocalBackStack("characterDetail")
        activity!!.supportFragmentManager
            .beginTransaction()
            .addToBackStack("characterDetail")
            .replace(R.id.fragmentContainer, SingleCharacterFragment.newInstance(viewModel, characterId))
            .commit()
        addLocalBackStack("characterDetail")
    }

    fun moveToEpisodeDetailScreen(viewModel: MainViewModel, episodeId: Int){
        setupBackButton()
        setToolBarTitle("Об эпизоде")
        activity!!.supportFragmentManager
            .beginTransaction()
            .addToBackStack(null)
            .replace(R.id.fragmentContainer, SingleEpisodeFragment.newInstance(viewModel, episodeId))
            .commit()
        addLocalBackStack("episodeDetail")
    }

    fun moveToLocationDetailScreen(viewModel: MainViewModel, locationId: Int, place: String){
        setupBackButton()
        setToolBarTitle("О локации")
        if(place != ""){
            activity!!.supportFragmentManager
                .beginTransaction()
                .addToBackStack(null)
                .replace(R.id.fragmentContainer, SingleLocationFragment.newInstance(viewModel, place))
                .commit()
        }
        else{
            activity!!.supportFragmentManager
                .beginTransaction()
                .addToBackStack(null)
                .replace(R.id.fragmentContainer, SingleLocationFragment.newInstance(viewModel, locationId))
                .commit()
        }
        addLocalBackStack("locationDetail")
    }

    fun moveToCharactersScreen(viewModel: MainViewModel){
        hideBackButton()
        //displayCurrentMenuPositionOnCharacter()
        setToolBarTitle("Персонажи")
        activity!!.supportFragmentManager.popBackStack("characters", POP_BACK_STACK_INCLUSIVE)
        popLocalBackStack("characters")
        activity!!.supportFragmentManager
            .beginTransaction()
            .addToBackStack("characters")
            .replace(R.id.fragmentContainer, CharacterFragment.newInstance(viewModel))
            .commit()
        addLocalBackStack("characters")
    }

    fun moveToEpisodesScreen(viewModel: MainViewModel){
        setupBackButton()
        setToolBarTitle("Эпизоды")
        activity!!.supportFragmentManager.popBackStack("episodes", POP_BACK_STACK_INCLUSIVE)
        popLocalBackStack("episodes")
        activity!!.supportFragmentManager
            .beginTransaction()
            .addToBackStack("episodes")
            .replace(R.id.fragmentContainer, EpisodeFragment.newInstance(viewModel))
            .commit()
        addLocalBackStack("episodes")
    }

    fun moveToLocationsScreen(viewModel: MainViewModel){
        setupBackButton()
        setToolBarTitle("Локации")
        activity!!.supportFragmentManager.popBackStack("locations", POP_BACK_STACK_INCLUSIVE)
        popLocalBackStack("locations")
        activity!!.supportFragmentManager
            .beginTransaction()
            .addToBackStack("locations")
            .replace(R.id.fragmentContainer, LocationFragment.newInstance(viewModel))
            .commit()
        addLocalBackStack("locations")
    }
    private fun setupBackButton(){
        activity!!.findViewById<MaterialToolbar>(R.id.toolBar).setNavigationIcon(R.drawable.ic_arrow_back)
    }

    fun displayCurrentMenuPosition(){
        when(localBackStack.last()){
            "characters" -> {
                activity!!.findViewById<BottomNavigationView>(R.id.bottomNavigationView).menu
                    .findItem(R.id.characters_fragment).isChecked = true
                activity!!.findViewById<MaterialToolbar>(R.id.toolBar).title = "Персонажи"
            }
            "episodes" -> {
                activity!!.findViewById<BottomNavigationView>(R.id.bottomNavigationView).menu
                    .findItem(R.id.episodes_fragment).isChecked = true
                activity!!.findViewById<MaterialToolbar>(R.id.toolBar).title = "Эпизоды"
            }
            "locations" -> {
                activity!!.findViewById<BottomNavigationView>(R.id.bottomNavigationView).menu
                    .findItem(R.id.locations_fragment).isChecked = true
                activity!!.findViewById<MaterialToolbar>(R.id.toolBar).title = "Локации"
            }
            "episodeDetail" -> {
                activity!!.findViewById<MaterialToolbar>(R.id.toolBar).title = "Об эпизоде"
            }
            "locationDetail" -> {
                activity!!.findViewById<MaterialToolbar>(R.id.toolBar).title = "О локации"
            }
            "characterDetail" -> {
                activity!!.findViewById<MaterialToolbar>(R.id.toolBar).title = "О персонаже"
            }
        }

    }
    fun popLocalBackStack(){
        localBackStack.removeLastOrNull()
    }

    private fun hideBackButton(){
        activity!!.findViewById<MaterialToolbar>(R.id.toolBar).navigationIcon = null
    }

    private fun setToolBarTitle(title: String){
        activity!!.findViewById<MaterialToolbar>(R.id.toolBar).title = title
    }

    private fun popLocalBackStack(name: String = ""){
        var lastIndex = localBackStack.lastIndex + 1
        for(index in localBackStack.indices){
            if(localBackStack[index] == name){
                lastIndex = index
                break
            }
        }
        for(i in localBackStack.lastIndex downTo lastIndex)
            localBackStack.removeLast()
    }

    private fun addLocalBackStack(name: String = "noname"){
        localBackStack.add(name)
    }
}