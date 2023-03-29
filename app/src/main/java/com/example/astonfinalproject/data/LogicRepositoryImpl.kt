package com.example.astonfinalproject.data

import android.app.Application
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.core.content.FileProvider
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.astonfinalproject.data.database.AppDatabase
import com.example.astonfinalproject.data.database.dao.CharacterInfoDao
import com.example.astonfinalproject.data.database.dao.DataStateDao
import com.example.astonfinalproject.data.database.dao.EpisodeInfoDao
import com.example.astonfinalproject.data.database.dao.LocationInfoDao
import com.example.astonfinalproject.data.database.dbModels.DataStateDbModel
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
import com.squareup.picasso.Picasso
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import javax.inject.Inject

import kotlin.concurrent.thread

class LogicRepositoryImpl @Inject constructor(
    private val mapper: Mapper,
    private val characterInfoDao: CharacterInfoDao,
    private val episodeInfoDao: EpisodeInfoDao,
    private val locationInfoDao: LocationInfoDao,
    private val dataStateDao: DataStateDao): LogicRepository {


    private val apiService = ApiFactory.apiService

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
        return Transformations.map(episodeInfoDao.getEpisodeInfoList()) {
            it.map { episode ->
                mapper.mapEpisodeDbModelToEntity(episode)
            }
        }

    }

    override fun getDataStateList(): LiveData<List<DataStateDbModel>> {
        return dataStateDao.getDataStateList()
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

    override suspend fun getEpisodesByCharacter(episodesUrl: List<String>): List<EpisodeInfo> {
        val episodesId: ArrayList<Int> = ArrayList()
        for(episodeUrl in episodesUrl){
            episodesId.add(mapper.mapURLtoId(episodeUrl))
        }
        return episodeInfoDao.getSelectedEpisodeInfoList(episodesId).map{
            mapper.mapEpisodeDbModelToEntity(it)
        }

    }

    override suspend fun getCharactersByUrlList(charactersUrl: List<String>): List<CharacterInfo> {
        val charactersId: ArrayList<Int> = ArrayList()
        for(characterUrl in charactersUrl){
            if(characterUrl != "")
            charactersId.add(mapper.mapURLtoId(characterUrl))
        }
        return characterInfoDao.getSelectedCharacterInfoList(charactersId).map{
            mapper.mapCharacterDbModelToEntity(it)
        }
    }

    override suspend fun getLocationInfo(id: Int): LocationInfo {
        return mapper.mapLocationDbModelToEntity(locationInfoDao.getLocationInfo(id))
    }

    override suspend fun getLocationInfo(place: String): LocationInfo {
        return mapper.mapLocationDbModelToEntity(locationInfoDao.getLocationInfo(place))
    }

    override suspend fun updateCharacterImagePath(id: Int, path: String) {
        characterInfoDao.updateImagePath(path,id)
    }

    override fun loadSingleCharacterInfo(id: Int) {
        apiService.getCharacter(id)
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe(object : DisposableSingleObserver<Any?>() {
                override fun onSuccess(obj: Any) {
                    val response = obj as CharactersResultDto
                    insertOrUpdateCharacter(response)
                    dispose()
                }

                override fun onError(e: Throwable) {
                    // handle the error case...
                    dispose()
                }
            })
    }


    override fun loadCharactersInfo(page: Int) {
        thread{
            dataStateDao.insertDataState(DataStateDbModel("characters",false))
        }
        apiService.getCharacters(page)
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe(object : DisposableSingleObserver<Any?>() {
                override fun onSuccess(obj: Any) {
                    val response = obj as CharactersPageResultDto
                    if(response.results != null){
                        for(characterDto in response.results) {
                            insertOrUpdateCharacter(characterDto)
                        }
                    }
                    if(response.info?.next != null){
                        loadCharactersInfo(page + 1)
                    }
                    else{
                        dataStateDao.insertDataState(DataStateDbModel("characters",true))
                    }
                    dispose()
                }

                override fun onError(e: Throwable) {
                    // handle the error case...
                    dispose()
                }
            })
    }

    private fun insertOrUpdateCharacter(characterDto: CharactersResultDto){
        val character = mapper.mapCharacterDtoToDbModel(characterDto)
        if(characterInfoDao.checkCharacterExists(character.id) == 1){
            val dbCharacter = characterInfoDao.getCharacterInfo(character.id)
            character.imageSrc = dbCharacter.imageSrc
            if(character == dbCharacter) return
        }
        Log.i("insertion", character.toString())
        characterInfoDao.insertCharacterInfo(character)

    }

    override fun loadSingleEpisodeInfo(id: Int) {
        apiService.getEpisode(id)
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe(object : DisposableSingleObserver<Any?>() {
                override fun onSuccess(obj: Any) {
                    val response = obj as EpisodesResultDto
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
        thread{
            dataStateDao.insertDataState(DataStateDbModel("episodes",false))
        }
        apiService.getEpisodes(page)
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe(object : DisposableSingleObserver<Any?>() {
                override fun onSuccess(obj: Any) {
                    val response = obj as EpisodesPageResultDto
                    if(response.results != null){
                        for(episodeDto in response.results) {
                            episodeInfoDao.insertEpisodeInfo(
                                mapper.mapEpisodeDtoToDbModel(episodeDto)
                            )
                        }
                    }
                    if(response.info?.next != null){
                        loadEpisodesInfo(page + 1)
                    }
                    else{
                        dataStateDao.insertDataState(DataStateDbModel("episodes",true))
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
        thread{
            dataStateDao.insertDataState(DataStateDbModel("locations",false))
        }
        apiService.getLocations(page)
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe(object : DisposableSingleObserver<Any?>() {
                override fun onSuccess(obj: Any) {
                    val response = obj as LocationsPageResultDto
                    if(response.info?.next != null){
                        loadLocationsInfo(page + 1)
                    }
                    else{
                        dataStateDao.insertDataState(DataStateDbModel("locations",true))
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