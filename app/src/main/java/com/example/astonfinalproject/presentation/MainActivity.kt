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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, CharacterFragment.newInstance(viewModel))
            .commit()
    }

    override fun onStart() {
        Log.i("onStart", "called")
        super.onStart()
    }

    override fun onResume() {
        Log.i("onResume", "called")
        super.onResume()
    }

    override fun onRestart() {
        Log.i("onReStart", "called")
        super.onRestart()
    }

    override fun onPause() {
        Log.i("onPause", "called")
        super.onPause()
    }

    override fun onStop() {
        Log.i("onStop", "called")
        super.onStop()
    }

    override fun onDestroy() {
        Log.i("onDestroy", "called")
        super.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        Log.i("onSaveInstance", "called")
        super.onSaveInstanceState(outState, outPersistentState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        Log.i("onRestore", "called")
        super.onRestoreInstanceState(savedInstanceState)
    }
}