package com.example.astonfinalproject.data

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.astonfinalproject.data.database.AppDatabase
import com.example.astonfinalproject.data.database.mapper.Mapper
import com.example.astonfinalproject.data.network.ApiFactory
import com.example.astonfinalproject.data.network.Model.Characters.CharactersPageResultDto
import com.example.astonfinalproject.data.network.Model.Characters.CharactersResultDto
import com.example.astonfinalproject.data.network.Model.Episodes.EpisodesPageResultDto
import com.example.astonfinalproject.data.network.Model.Episodes.EpisodesResultDto
import com.example.astonfinalproject.data.network.Model.Locations.LocationsPageResultDto
import com.example.astonfinalproject.data.network.Model.Locations.LocationsResultDto
import com.example.astonfinalproject.domain.LogicRepository
import com.example.astonfinalproject.domain.Model.CharacterInfo
import com.example.astonfinalproject.domain.Model.EpisodeInfo
import com.example.astonfinalproject.domain.Model.LocationInfo
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers


class LogicRepositoryImpl(application: Application): LogicRepository {

    private val characterInfoDao = AppDatabase.getInstance(application).characterInfoDao()
    private val episodeInfoDao = AppDatabase.getInstance(application).episodeInfoDao()
    private val locationInfoDao = AppDatabase.getInstance(application).locationInfoDao()
    private val apiService = ApiFactory.apiService
    private val mapper = Mapper()

    override fun getCharactersList(): LiveData<List<CharacterInfo>> {
        return Transformations.map(characterInfoDao.getCharacterInfoList()){
            it.map{ character ->
                mapper.mapCharacterDbModelToEntity(character)
            }
        }
    }

    override suspend fun getCharacterInfo(id: Int): CharacterInfo {
        return mapper.mapCharacterDbModelToEntity(characterInfoDao.getCharacterInfo(id))
    }

    override fun getEpisodesList(): LiveData<List<EpisodeInfo>> {
        return Transformations.map(episodeInfoDao.getEpisodeInfoList()){
            it.map{ episode ->
                mapper.mapEpisodeDbModelToEntity(episode)
            }
        }
    }

    override suspend fun getEpisodeInfo(id: Int): EpisodeInfo {
        return mapper.mapEpisodeDbModelToEntity(episodeInfoDao.getEpisodeInfo(id))
    }

    override fun getLocationsList(): LiveData<List<LocationInfo>> {
        return Transformations.map(locationInfoDao.getLocationInfoList()){
            it.map{ location ->
                mapper.mapLocationDbModelToEntity(location)
            }
        }
    }

    override suspend fun getLocationInfo(id: Int): LocationInfo {
        return mapper.mapLocationDbModelToEntity(locationInfoDao.getLocationInfo(id))
    }

    override fun loadSingleCharacterInfo(id: Int) {
        apiService.getCharacter(id)
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe(object : DisposableSingleObserver<Any?>() {
                override fun onSuccess(obj: Any) {
                    val response = obj as CharactersResultDto
                    Log.i("loading","got character, id $id")
                    characterInfoDao.insertCharacterInfo(mapper.mapCharacterDtoToDbModel(response))
                    dispose()
                }

                override fun onError(e: Throwable) {
                    // handle the error case...
                    dispose()
                }
            })
    }


    override fun loadCharactersInfo(page: Int) {
        Log.i("load", "Loading characters")
        apiService.getCharacters(page)
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe(object : DisposableSingleObserver<Any?>() {
                override fun onSuccess(obj: Any) {
                    val response = obj as CharactersPageResultDto
                    Log.i("loading","got characters, page $page")
                    if(response.info?.next != null){
                        loadCharactersInfo(page + 1)
                    }
                    else{
                        Log.i("loading","Всего было = $page")
                    }
                    if(response.results != null){
                        for(characterDto in response.results) {
                            characterInfoDao.insertCharacterInfo(
                                mapper.mapCharacterDtoToDbModel(characterDto)
                            )
                        }
                    }
                    dispose()
                }

                override fun onError(e: Throwable) {
                    // handle the error case...
                    dispose()
                }
            })
    }

    override fun loadSingleEpisodeInfo(id: Int) {
        apiService.getEpisode(id)
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe(object : DisposableSingleObserver<Any?>() {
                override fun onSuccess(obj: Any) {
                    val response = obj as EpisodesResultDto
                    Log.i("loading","got character, id $id")
                    episodeInfoDao.insertEpisodeInfo(mapper.mapEpisodeDtoToDbModel(response))
                    dispose()
                }

                override fun onError(e: Throwable) {
                    // handle the error case...
                    dispose()
                }
            })
    }

    override fun loadEpisodesInfo(page: Int) {
        Log.i("load", "Loading episodes")
        apiService.getEpisodes(page)
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe(object : DisposableSingleObserver<Any?>() {
                override fun onSuccess(obj: Any) {
                    val response = obj as EpisodesPageResultDto
                    Log.i("loading","got episodes, page $page")
                    Log.i("episodes:", response.results.toString())
                    if(response.info?.next != null){
                        loadEpisodesInfo(page + 1)
                    }
                    else{
                        Log.i("loading","Всего было = $page")
                    }
                    if(response.results != null){
                        for(episodeDto in response.results) {
                            episodeInfoDao.insertEpisodeInfo(
                                mapper.mapEpisodeDtoToDbModel(episodeDto)
                            )
                        }
                    }
                    dispose()
                }

                override fun onError(e: Throwable) {
                    // handle the error case...
                    dispose()
                }
            })
    }

    override fun loadSingleLocationInfo(id: Int) {
        apiService.getLocation(id)
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe(object : DisposableSingleObserver<Any?>() {
                override fun onSuccess(obj: Any) {
                    val response = obj as LocationsResultDto
                    Log.i("loading","got character, id $id")
                    locationInfoDao.insertLocationInfo(mapper.mapLocationDtoToDbModel(response))
                    dispose()
                }

                override fun onError(e: Throwable) {
                    // handle the error case...
                    dispose()
                }
            })
    }

    override fun loadLocationsInfo(page: Int) {
        Log.i("load", "Loading locations")
        apiService.getLocations(page)
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe(object : DisposableSingleObserver<Any?>() {
                override fun onSuccess(obj: Any) {
                    val response = obj as LocationsPageResultDto
                    Log.i("loading","got locations, page $page")
                    if(response.info?.next != null){
                        loadLocationsInfo(page + 1)
                    }
                    else{
                        Log.i("loading","Всего было = $page")
                    }
                    if(response.results != null){
                        for(locationDto in response.results) {
                            locationInfoDao.insertLocationInfo(
                                mapper.mapLocationDtoToDbModel(locationDto)
                            )
                        }
                    }
                    dispose()
                }

                override fun onError(e: Throwable) {
                    // handle the error case...
                    dispose()
                }
            })
    }
}