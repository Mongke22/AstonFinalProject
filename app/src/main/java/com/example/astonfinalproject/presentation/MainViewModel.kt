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

class MainViewModel(application: Application) : AndroidViewModel(application) {

    companion object {

        const val UNKNOWN_INT = -1

        enum class Screen {
            CHARACTER_DETAIL, CHARACTERS, EPISODE_DETAIL, EPISODES, LOCATION_DETAIL, LOCATIONS
        }
    }

    lateinit var navigator: Navigator

    private val repository = LogicRepositoryImpl(application)
    private val getCharacterInfoUseCase = GetCharacterInfoUseCase(repository)
    private val getCharactersListUseCase = GetCharactersListUseCase(repository)
    private val getEpisodeInfoUseCase = GetEpisodeInfoUseCase(repository)
    private val getEpisodesListUseCase = GetEpisodesListUseCase(repository)
    private val getEpisodesByCharacterUseCase = GetEpisodesByCharacterUseCase(repository)
    private val getCharactersByEpisodeUseCase = GetCharactersByEpisodeUseCase(repository)
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

    private val _episodeInfo = MutableLiveData<EpisodeInfo>()
    val episodeInfo: LiveData<EpisodeInfo>
        get() = _episodeInfo

    private val _locationInfo = MutableLiveData<LocationInfo>()
    val locationInfo: LiveData<LocationInfo>
        get() = _locationInfo

    private var _characterEpisodeList = MutableLiveData<List<EpisodeInfo>>()
    val characterEpisodeList: LiveData<List<EpisodeInfo>>
        get() = _characterEpisodeList

    private var _episodeCharacterList = MutableLiveData<List<CharacterInfo>>()
    val episodeCharacterList: LiveData<List<CharacterInfo>>
        get() = _episodeCharacterList

    fun loadCharacters() {
        loadDataUseCase.loadCharactersList(1)
    }

    fun loadEpisodes() {
        loadDataUseCase.loadEpisodesList(1)
    }

    fun loadLocations() {
        loadDataUseCase.loadLocationsList(1)
    }

    fun loadCharacter(id: Int) {
        loadDataUseCase.loadCharacter(id)
    }

    fun loadEpisode(id: Int){
        loadDataUseCase.loadEpisode(id)
    }

    fun getCharacter(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val item: CharacterInfo = getCharacterInfoUseCase(id)
            val episodesList: List<EpisodeInfo> = getEpisodesByCharacterUseCase(item.episodes)
            _characterEpisodeList.postValue(episodesList)
            _characterInfo.postValue(item)
        }
    }

    fun getEpisode(id: Int){
        viewModelScope.launch(Dispatchers.IO) {
            val item: EpisodeInfo = getEpisodeInfoUseCase(id)
            val characterList: List<CharacterInfo> = getCharactersByEpisodeUseCase(item.characters)
            _episodeCharacterList.postValue(characterList)
            _episodeInfo.postValue(item)
        }
    }

    fun updateImagePath(id: Int, path: String) {
        viewModelScope.launch(Dispatchers.IO) {
            updateImagePathUseCase(id, path)
        }
    }

    fun moveToScreen(moveToScreen: Screen, id: Int = UNKNOWN_INT, place: String = "") {
        when(moveToScreen){
            Screen.LOCATION_DETAIL -> {
                navigator.moveToLocationDetailScreen(this, id)
            }
            Screen.CHARACTER_DETAIL -> {
                navigator.moveToCharacterDetailScreen(this, id)
            }
            Screen.EPISODE_DETAIL -> {
                navigator.moveToEpisodeDetailScreen(this, id)
            }
            Screen.CHARACTERS -> {
                navigator.moveToCharactersScreen(this)
            }
            Screen.EPISODES -> {
                navigator.moveToEpisodesScreen(this)
            }
            Screen.LOCATIONS -> {
                navigator.moveToLocationsScreen(this)
            }
            else -> {
                throw Exception("Unknown screen $moveToScreen")
            }
        }
    }


    override fun onCleared() {
        Log.i("viewModel", "cleared")
        super.onCleared()
    }
}