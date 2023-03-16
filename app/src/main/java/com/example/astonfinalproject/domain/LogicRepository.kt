package com.example.astonfinalproject.domain

import androidx.lifecycle.LiveData
import com.example.astonfinalproject.domain.Model.CharacterInfo
import com.example.astonfinalproject.domain.Model.EpisodeInfo
import com.example.astonfinalproject.domain.Model.LocationInfo

interface LogicRepository {

    fun getCharactersList(): LiveData<List<CharacterInfo>>

    suspend fun getCharacterInfo(id: Int): CharacterInfo

    fun getEpisodesList(): LiveData<List<EpisodeInfo>>

    suspend fun getEpisodeInfo(id: Int): EpisodeInfo

    fun getLocationsList(): LiveData<List<LocationInfo>>

    suspend fun getLocationInfo(id: Int): LocationInfo


    fun loadSingleCharacterInfo(id: Int)

    fun loadCharactersInfo(page: Int)

    fun loadSingleEpisodeInfo(id: Int)

    fun loadEpisodesInfo(page: Int)

    fun loadSingleLocationInfo(id: Int)

    fun loadLocationsInfo(page: Int)

}