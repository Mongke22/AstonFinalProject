package com.example.astonfinalproject.data.database.mapper

import com.example.astonfinalproject.data.database.dbModels.CharacterInfoDbModel
import com.example.astonfinalproject.data.database.dbModels.EpisodeInfoDbModel
import com.example.astonfinalproject.data.database.dbModels.LocationInfoDbModel
import com.example.astonfinalproject.data.network.Model.Characters.CharactersResultDto
import com.example.astonfinalproject.data.network.Model.Episodes.EpisodesResultDto
import com.example.astonfinalproject.data.network.Model.Locations.LocationsResultDto
import com.example.astonfinalproject.domain.Model.CharacterInfo
import com.example.astonfinalproject.domain.Model.EpisodeInfo
import com.example.astonfinalproject.domain.Model.LocationInfo

class Mapper {
    fun mapCharacterDtoToDbModel(characterDto: CharactersResultDto): CharacterInfoDbModel {
        return CharacterInfoDbModel(
            characterDto.url ?: "unknown",
            characterDto.id,
            characterDto.name ?: "unknown",
            characterDto.species ?: "unknown",
            characterDto.status ?: "unknown",
            characterDto.gender ?: "unknown",
            characterDto.image ?: "unknown",
            characterDto.origin?.name ?: "unknown",
            characterDto.origin?.url ?: "unknown",
            characterDto.location?.name ?: "unknown",
            characterDto.location?.url ?: "unknown",
            characterDto.episode
        )
    }

    fun mapEpisodeDtoToDbModel(episodeDto: EpisodesResultDto): EpisodeInfoDbModel {
        return EpisodeInfoDbModel(
            episodeDto.url ?: "unknown",
            episodeDto.id,
            episodeDto.name ?: "unknown",
            episodeDto.air_date ?: "unknown",
            episodeDto.episode ?: "unknown",
            episodeDto.characters
        )
    }

    fun mapLocationDtoToDbModel(locationDto: LocationsResultDto): LocationInfoDbModel {
        return LocationInfoDbModel(
            locationDto.url ?: "unknown",
            locationDto.id,
            locationDto.name ?: "unknown",
            locationDto.type ?: "unknown",
            locationDto.dimension ?: "unknown",
            locationDto.characters
        )
    }


    fun mapCharacterDbModelToEntity(characterDb: CharacterInfoDbModel): CharacterInfo {
        return CharacterInfo(
            characterDb.id,
            characterDb.name,
            characterDb.imageSrc,
            characterDb.status,
            characterDb.gender,
            characterDb.species
        )
    }

    fun mapEpisodeDbModelToEntity(episodeDb: EpisodeInfoDbModel): EpisodeInfo {
        return EpisodeInfo(
            episodeDb.id,
            episodeDb.name,
            episodeDb.episodeNumber,
            episodeDb.airDate
        )
    }

    fun mapLocationDbModelToEntity(locationDb: LocationInfoDbModel): LocationInfo {
        return LocationInfo(
            locationDb.id,
            locationDb.name,
            locationDb.type,
            locationDb.dimension
        )
    }
}