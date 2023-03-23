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


class LogicRepositoryImpl(application: Application): LogicRepository {

    private val characterInfoDao = AppDatabase.getInstance(application).characterInfoDao()
    private val episodeInfoDao = AppDatabase.getInstance(application).episodeInfoDao()
    private val locationInfoDao = AppDatabase.getInstance(application).locationInfoDao()
    private var context = application
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
        return Transformations.map(episodeInfoDao.getEpisodeInfoList()) {
            it.map { episode ->
                mapper.mapEpisodeDbModelToEntity(episode)
            }
        }

    }

    override suspend fun getEpisodesByCharacter(episodesUrl: List<String>): List<EpisodeInfo> {
        val episodesId: ArrayList<Int> = ArrayList()
        for(episodeUrl in episodesUrl){
            episodesId.add(mapper.mapURLtoId(episodeUrl))
        }
        val result = episodeInfoDao.getSelectedEpisodeInfoList(episodesId)
        val a = 14
        return result.map{
            mapper.mapEpisodeDbModelToEntity(it)
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
        apiService.getCharacters(page)
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe(object : DisposableSingleObserver<Any?>() {
                override fun onSuccess(obj: Any) {
                    val response = obj as CharactersPageResultDto
                    if(response.results != null){
                        for(characterDto in response.results) {
                            characterInfoDao.insertCharacterInfo(
                                mapper.mapCharacterDtoToDbModel(characterDto)
                            )
                        }
                    }
                    if(response.info?.next != null){
                        loadCharactersInfo(page + 1)
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

    private fun loadImageForCharacter(url: String, id: Int){
        if(url.isEmpty()) return
        val uiHandler = Handler(Looper.getMainLooper())
        uiHandler.post{
            Picasso.with(context)
                .load(url)
                .into(object: com.squareup.picasso.Target {
                    override fun onBitmapLoaded(bitmap: Bitmap, from: Picasso.LoadedFrom?) {
                        val path = saveImageToPhone(bitmap, "character$id")
                        Log.i("path", path?: "empty path")
                    }

                    override fun onBitmapFailed(errorDrawable: Drawable?) {
                        Log.i("imageLoading","failed")
                    }

                    override fun onPrepareLoad(placeHolderDrawable: Drawable?) {

                    }

                })
        }


    }

    private fun saveImageToPhone(image: Bitmap, fileName: String): String?{
        val imagesFolder = File(context.cacheDir, "images")
        var path: String? = null
        try {
            imagesFolder.mkdirs()
            val file = File(imagesFolder, fileName)
            val stream = FileOutputStream(file)
            image.compress(Bitmap.CompressFormat.PNG, 90, stream)
            stream.flush()
            stream.close()
            path = file.absolutePath
        } catch (e: IOException) {
            Log.i("IOException", "${e.message}")
        }
        return path
    }
}