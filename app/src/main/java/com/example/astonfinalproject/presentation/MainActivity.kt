package com.example.astonfinalproject.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import com.example.astonfinalproject.R
import com.example.astonfinalproject.databinding.ActivityMainBinding
import com.example.astonfinalproject.data.network.ApiFactory
import com.example.astonfinalproject.presentation.fragments.CharacterFragment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val apiService = ApiFactory.apiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Log.i("onCreate", "called")
        val test = apiService.getCharacters()
        test
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { it ->
                    val movies = it.results
                    Log.i("movies", it.toString())
                },
                { error ->
                    // Логируем ошибку
                    Log.e("getCharacters", error.toString())
                }
            )
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, CharacterFragment.newInstance())
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