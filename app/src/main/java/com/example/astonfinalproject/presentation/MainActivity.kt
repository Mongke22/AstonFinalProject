package com.example.astonfinalproject.presentation

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.example.astonfinalproject.R
import com.example.astonfinalproject.data.LogicRepositoryImpl
import com.example.astonfinalproject.databinding.ActivityMainBinding
import com.example.astonfinalproject.data.network.ApiFactory
import com.example.astonfinalproject.domain.LogicRepository
import com.example.astonfinalproject.presentation.fragments.CharacterFragment
import com.example.astonfinalproject.presentation.fragments.EpisodeFragment
import com.example.astonfinalproject.presentation.fragments.LocationFragment
import com.example.astonfinalproject.presentation.fragments.SingleCharacterFragment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var navigator: Navigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navigator = Navigator()
        navigator.activity = this

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.navigator = navigator

        viewModel.loadEpisodes()

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
            binding.toolBar.title = "Персонажи"
            viewModel.moveToScreen(MainViewModel.Companion.Screen.CHARACTERS)
        }
    }

    private fun setBottomNavMenu() {
        binding.bottomNavigationView.setOnItemReselectedListener {
            when (it.itemId) {
                R.id.characters_fragment -> {
                    binding.toolBar.title = "Персонажи"
                    viewModel.moveToScreen(MainViewModel.Companion.Screen.CHARACTERS)
                }
                R.id.episodes_fragment -> {
                    binding.toolBar.title = "Персонажи"
                    viewModel.moveToScreen(MainViewModel.Companion.Screen.EPISODES)
                }
                R.id.locations_fragment -> {
                    binding.toolBar.title = "Персонажи"
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