package com.example.astonfinalproject.domain.usecase

import com.example.astonfinalproject.domain.LogicRepository

class LoadDataUseCase(
    private val repository: LogicRepository
) {
    fun loadCharactersList(page: Int){
        repository.loadCharactersInfo(page)
    }

    fun loadCharacter(id: Int){
        repository.loadSingleCharacterInfo(id)
    }

    fun loadEpisodesList(page: Int){
        repository.loadEpisodesInfo(page)
    }

    fun loadEpisode(id: Int){
        repository.loadSingleEpisodeInfo(id)
    }

    fun loadLocationsList(page: Int){
        repository.loadLocationsInfo(page)
    }

    fun loadLocation(id: Int){
        repository.loadSingleLocationInfo(id)
    }
}