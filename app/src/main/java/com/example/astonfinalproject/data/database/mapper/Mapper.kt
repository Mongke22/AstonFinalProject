package com.example.astonfinalproject.data.database.mapper

import com.example.astonfinalproject.data.database.dbModels.CharacterInfoDbModel
import com.example.astonfinalproject.data.database.dbModels.EpisodeInfoDbModel
import com.example.astonfinalproject.data.database.dbModels.LocationInfoDbModel
import com.example.astonfinalproject.data.network.Model.Characters.CharactersResultDto
import com.example.astonfinalproject.data.network.Model.Episodes.EpisodesResultDto
import com.example.astonfinalproject.data.network.Model.Locations.LocationsResultDto

class Mapper {
    fun mapCharacterDtoToDbModel(characterDto: CharactersResultDto): CharacterInfoDbModel{
        return CharacterInfoDbModel(
            characterDto.url?:"unknown",
            characterDto.name?:"unknown",
            characterDto.species?:"unknown",
            characterDto.status?:"unknown",
            characterDto.gender?:"unknown",
            characterDto.image?:"unknown",
            characterDto.origin?.name?:"unknown",
            characterDto.origin?.url?:"unknown",
            characterDto.location?.name?:"unknown",
            characterDto.location?.url?:"unknown",
            characterDto.episode
        )
    }
    fun mapEpisodeDtoToDbModel(episodeDto: EpisodesResultDto): EpisodeInfoDbModel {
        return EpisodeInfoDbModel(
            episodeDto.url?:"unknown",
            episodeDto.name?:"unknown",
            episodeDto.air_date?:"unknown",
            episodeDto.episode?:"unknown",
            episodeDto.characters
        )
    }
    fun mapLocationDtoToDbModel(locationDto: LocationsResultDto): LocationInfoDbModel {
        return LocationInfoDbModel(
            locationDto.url?:"unknown",
            locationDto.name?:"unknown",
            locationDto.type?:"unknown",
            locationDto.dimension?:"unknown",
            locationDto.characters
        )
    }
}