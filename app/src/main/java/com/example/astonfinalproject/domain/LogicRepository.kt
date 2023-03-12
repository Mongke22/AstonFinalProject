package com.example.astonfinalproject.domain

import androidx.lifecycle.LiveData
import com.example.astonfinalproject.domain.Model.CharacterInfo
import com.example.astonfinalproject.domain.Model.EpisodeInfo
import com.example.astonfinalproject.domain.Model.LocationInfo

interface LogicRepository {

    fun getCharactersList(): LiveData<List<CharacterInfo>>

    suspend fun getCharacterInfo(url: String): CharacterInfo

    fun getEpisodesList(): LiveData<List<EpisodeInfo>>

    suspend fun getEpisodeInfo(url: String): EpisodeInfo

    fun getLocationsList(): LiveData<List<LocationInfo>>

    suspend fun getLocationInfo(url: String): LocationInfo


    fun loadSingleCharacterInfo(url: String)

    fun loadCharactersInfo()

    fun loadSingleEpisodeInfo(url: String)

    fun loadEpisodesInfo()

    fun loadSingleLocationInfo(url: String)

    fun loadLocationsInfo()

}