package com.example.astonfinalproject.presentation.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.astonfinalproject.data.database.dbModels.DataStateDbModel
import com.example.astonfinalproject.domain.Model.CharacterInfo
import com.example.astonfinalproject.domain.Model.EpisodeInfo
import com.example.astonfinalproject.domain.Model.LocationInfo
import com.example.astonfinalproject.domain.usecase.*
import com.example.astonfinalproject.presentation.Navigator
import com.example.astonfinalproject.presentation.filter.model.CharacterFilter
import com.example.astonfinalproject.presentation.filter.model.EpisodeFilter
import com.example.astonfinalproject.presentation.filter.model.LocationFilter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
            private val getCharacterInfoUseCase: GetCharacterInfoUseCase,
            private val getCharactersListUseCase: GetCharactersListUseCase,
            private val getEpisodeInfoUseCase: GetEpisodeInfoUseCase,
            private val getEpisodesListUseCase: GetEpisodesListUseCase,
            private val getEpisodesByCharacterUseCase: GetEpisodesByCharacterUseCase,
            private val getCharactersByUrlListUseCase: GetCharactersByUrlListUseCase,
            private val getLocationInfoUseCase: GetLocationInfoUseCase,
            private val getLocationsListUseCase: GetLocationsListUseCase,
            private val updateImagePathUseCase: UpdateImagePathUseCase,
            private val loadDataUseCase: LoadDataUseCase
) : ViewModel() {

    companion object {

        const val UNKNOWN_INT = -1

        enum class Screen {
            CHARACTER_DETAIL, CHARACTERS, EPISODE_DETAIL, EPISODES, LOCATION_DETAIL, LOCATIONS
        }
    }

    lateinit var navigator: Navigator

    private var _characterList = getCharactersListUseCase()
    val characterList: LiveData<List<CharacterInfo>>
        get() = _characterList

    private var _episodeList = getEpisodesListUseCase()
    val episodesList: LiveData<List<EpisodeInfo>>
        get() = _episodeList

    private var _locationList = getLocationsListUseCase()
    val locationsList: LiveData<List<LocationInfo>>
        get() = _locationList

    private var _stateList = loadDataUseCase.getDataStateList()
    val stateList: LiveData<List<DataStateDbModel>>
        get() = _stateList

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

    private var _locationCharacterList = MutableLiveData<List<CharacterInfo>>()
    val locationCharacterList: LiveData<List<CharacterInfo>>
        get() = _locationCharacterList

    private val _dataAvailable = MutableLiveData<Boolean>()
    val dataAvailable: LiveData<Boolean>
        get() = _dataAvailable

    private var _filterCharacter = MutableLiveData<CharacterFilter>()
    val filterCharacter: LiveData<CharacterFilter>
        get() = _filterCharacter

    private var _filterEpisode = MutableLiveData<EpisodeFilter>()
    val filterEpisode: LiveData<EpisodeFilter>
        get() = _filterEpisode

    private var _filterLocation = MutableLiveData<LocationFilter>()
    val filterLocation: LiveData<LocationFilter>
        get() = _filterLocation

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

    fun loadLocation(id: Int, place: String = ""){
        if(place == ""){
            loadDataUseCase.loadLocation(id)
        }
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
            val characterList: List<CharacterInfo> = getCharactersByUrlListUseCase(item.characters)
            _episodeCharacterList.postValue(characterList)
            _episodeInfo.postValue(item)
        }
    }

    fun getLocation(id: Int, place: String){
        viewModelScope.launch(Dispatchers.IO) {
            var item: LocationInfo = if(place == ""){
                getLocationInfoUseCase(id)
            } else{
                getLocationInfoUseCase(place)
            }
            Log.i("getLocation", item.residents.toString())
            val characterList: List<CharacterInfo> = getCharactersByUrlListUseCase(item.residents)
            _locationCharacterList.postValue(characterList)
            _locationInfo.postValue(item)
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
                navigator.moveToLocationDetailScreen(this, id, place)
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

    fun hideFragmentIfNoAvailableData(noAvailableData: Boolean){
        _dataAvailable.value = !noAvailableData
    }

    fun setFilter(filter: CharacterFilter){
        _filterCharacter.value = filter
    }

    fun setFilter(filter: EpisodeFilter){
        _filterEpisode.value = filter
    }

    fun setFilter(filter: LocationFilter){
        _filterLocation.value = filter
    }

    override fun onCleared() {
        Log.i("viewModel", "cleared")
        super.onCleared()
    }
}