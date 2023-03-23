package com.example.astonfinalproject.presentation

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.astonfinalproject.data.LogicRepositoryImpl
import com.example.astonfinalproject.domain.Model.CharacterInfo
import com.example.astonfinalproject.domain.Model.EpisodeInfo
import com.example.astonfinalproject.domain.Model.LocationInfo
import com.example.astonfinalproject.domain.usecase.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(application: Application): AndroidViewModel(application) {

    private val repository = LogicRepositoryImpl(application)
    private val getCharacterInfoUseCase = GetCharacterInfoUseCase(repository)
    private val getCharactersListUseCase = GetCharactersListUseCase(repository)
    private val getEpisodeInfoUseCase = GetEpisodeInfoUseCase(repository)
    private val getEpisodesListUseCase = GetEpisodesListUseCase(repository)
    private val getEpisodesByCharacterUseCase = GetEpisodesByCharacterUseCase(repository)
    private val getLocationInfoUseCase = GetLocationInfoUseCase(repository)
    private val getLocationsListUseCase = GetLocationsListUseCase(repository)
    private val updateImagePathUseCase = UpdateImagePathUseCase(repository)
    private val loadDataUseCase = LoadDataUseCase(repository)

    private var _characterList = getCharactersListUseCase()
    val characterList: LiveData<List<CharacterInfo>>
        get() = _characterList

    private var _episodeList = getEpisodesListUseCase()
    val episodesList: LiveData<List<EpisodeInfo>>
        get() = _episodeList

    private var _locationList = getLocationsListUseCase()
    val locationsList: LiveData<List<LocationInfo>>
        get() = _locationList

    private val _characterInfo = MutableLiveData<CharacterInfo>()
    val characterInfo: LiveData<CharacterInfo>
        get() = _characterInfo

    private var _characterEpisodeList = MutableLiveData<List<EpisodeInfo>>()
    val characterEpisodeList: LiveData<List<EpisodeInfo>>
        get() = _episodeList

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

    fun getCharacter(id: Int){
        viewModelScope.launch(Dispatchers.IO) {
            val item: CharacterInfo = getCharacterInfoUseCase(id)
            val stringUrlList = getStringListFromString(item.episodes.getOrNull(0))
            val episodesList: List<EpisodeInfo> = getEpisodesByCharacterUseCase(stringUrlList)
            _characterEpisodeList.postValue(episodesList)
            _characterInfo.postValue(item)
        }
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

    private fun getStringListFromString(str: String?): List<String>{
        return str?.substring(1,str.lastIndex)?.split(',') ?: listOf()
    }
}