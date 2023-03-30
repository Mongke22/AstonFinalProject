package com.example.astonfinalproject.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.example.astonfinalproject.R
import com.example.astonfinalproject.databinding.ActivityMainBinding
import com.example.astonfinalproject.presentation.fragments.BaseFragment
import com.example.astonfinalproject.presentation.fragments.CharacterFragment
import com.example.astonfinalproject.presentation.viewModel.MainViewModel
import com.example.astonfinalproject.presentation.viewModel.ViewModelFactory
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var viewModel: MainViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var navigator: Navigator

    private val component by lazy {
        (application as AstonApp).component
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navigator.activity = this

        viewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
        viewModel.navigator = navigator

        viewModel.loadCharacters()
        viewModel.loadEpisodes()
        viewModel.loadLocations()

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, CharacterFragment.newInstance(viewModel))
            .commit()

        setNavigationMenu()

    }

    private fun setNavigationMenu() {
        setToolBarMenu()
        setBottomNavMenu()
    }

    private fun setToolBarMenu() {
        binding.toolBar.setNavigationOnClickListener {
            supportFragmentManager.popBackStack()
            BaseFragment.viewModel.updateMenu()
        }
    }

    private fun setBottomNavMenu() {
        binding.bottomNavigationView.setOnItemReselectedListener {
            when (it.itemId) {
                R.id.characters_fragment -> {
                    viewModel.moveToScreen(MainViewModel.Companion.Screen.CHARACTERS)
                }
                R.id.episodes_fragment -> {
                    viewModel.moveToScreen(MainViewModel.Companion.Screen.EPISODES)
                }
                R.id.locations_fragment -> {
                    viewModel.moveToScreen(MainViewModel.Companion.Screen.LOCATIONS)
                }
            }
        }
    }


    override fun onDestroy() {
        Log.i("onDestroy", "called")
        navigator.activity = null
        super.onDestroy()
    }
}