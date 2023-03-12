package com.example.astonfinalproject.domain.usecase

import com.example.astonfinalproject.domain.LogicRepository

class LoadDataUseCase(
    private val repository: LogicRepository
) {
    fun loadCharactersList(){
        repository.loadCharactersInfo()
    }

    fun loadCharacter(url: String){
        repository.loadSingleCharacterInfo(url)
    }

    fun loadEpisodesList(){
        repository.loadEpisodesInfo()
    }

    fun loadEpisode(url: String){
        repository.loadSingleEpisodeInfo(url)
    }

    fun loadLocationsList(){
        repository.loadLocationsInfo()
    }

    fun loadLocation(url: String){
        repository.loadSingleLocationInfo(url)
    }
}