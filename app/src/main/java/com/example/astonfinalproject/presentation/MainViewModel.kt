package com.example.astonfinalproject.presentation

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.astonfinalproject.data.LogicRepositoryImpl
import com.example.astonfinalproject.domain.Model.CharacterInfo
import com.example.astonfinalproject.domain.usecase.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(application: Application): AndroidViewModel(application) {

    private val repository = LogicRepositoryImpl(application)
    private val getCharacterInfoUseCase = GetCharacterInfoUseCase(repository)
    private val getCharactersListUseCase = GetCharactersListUseCase(repository)
    private val getEpisodeInfoUseCase = GetEpisodeInfoUseCase(repository)
    private val getEpisodesListUseCase = GetEpisodesListUseCase(repository)
    private val getLocationInfoUseCase = GetLocationInfoUseCase(repository)
    private val getLocationsListUseCase = GetLocationsListUseCase(repository)
    private val updateImagePathUseCase = UpdateImagePathUseCase(repository)
    private val loadDataUseCase = LoadDataUseCase(repository)

    private var _characterList = getCharactersListUseCase()
    val characterList: LiveData<List<CharacterInfo>>
        get() = _characterList

    fun loadCharacters(){
        loadDataUseCase.loadCharactersList(1)
    }

    fun loadEpisodes(){
        loadDataUseCase.loadEpisodesList(1)
    }

    fun loadLocations(){
        loadDataUseCase.loadLocationsList(1)
    }

    fun loadCharacter(id: Int){
        loadDataUseCase.loadCharacter(id)
    }

    fun updateImagePath(id: Int, path: String){
        viewModelScope.launch(Dispatchers.IO){
            updateImagePathUseCase(id, path)
        }
    }


    override fun onCleared() {
        Log.i("viewModel","cleared")
        super.onCleared()
    }
}