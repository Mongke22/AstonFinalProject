package com.example.astonfinalproject.domain.usecase

import androidx.lifecycle.LiveData
import com.example.astonfinalproject.data.database.dbModels.DataStateDbModel
import com.example.astonfinalproject.domain.LogicRepository
import javax.inject.Inject

class LoadDataUseCase @Inject constructor(
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

    fun getDataStateList(): LiveData<List<DataStateDbModel>> {
        return repository.getDataStateList()
    }
}