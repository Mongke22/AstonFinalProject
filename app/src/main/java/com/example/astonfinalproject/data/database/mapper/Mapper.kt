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
import com.squareup.picasso.Picasso

class Mapper {

    companion object {
        private const val UNKNOWN_STRING = "unknown"
    }

    fun mapCharacterDtoToDbModel(characterDto: CharactersResultDto): CharacterInfoDbModel {
        return CharacterInfoDbModel(
            characterDto.url?.lowercase() ?: UNKNOWN_STRING,
            characterDto.id,
            characterDto.name?.lowercase() ?: UNKNOWN_STRING,
            characterDto.species?.lowercase() ?: UNKNOWN_STRING,
            characterDto.status?.lowercase() ?: UNKNOWN_STRING,
            characterDto.gender?.lowercase() ?: UNKNOWN_STRING,
            UNKNOWN_STRING,
            characterDto.image?.lowercase() ?: UNKNOWN_STRING,
            characterDto.origin?.name?.lowercase() ?: UNKNOWN_STRING,
            characterDto.origin?.url?.lowercase() ?: UNKNOWN_STRING,
            characterDto.location?.name?.lowercase() ?: UNKNOWN_STRING,
            characterDto.location?.url?.lowercase() ?: UNKNOWN_STRING,
            characterDto.episode
        )
    }

    fun mapEpisodeDtoToDbModel(episodeDto: EpisodesResultDto): EpisodeInfoDbModel {
        return EpisodeInfoDbModel(
            episodeDto.url?.lowercase() ?: UNKNOWN_STRING,
            episodeDto.id,
            episodeDto.name?.lowercase() ?: UNKNOWN_STRING,
            episodeDto.air_date?.lowercase() ?: UNKNOWN_STRING,
            episodeDto.episode?.lowercase() ?: UNKNOWN_STRING,
            episodeDto.characters
        )
    }

    fun mapLocationDtoToDbModel(locationDto: LocationsResultDto): LocationInfoDbModel {
        return LocationInfoDbModel(
            locationDto.url?.lowercase() ?: UNKNOWN_STRING,
            locationDto.id,
            locationDto.name?.lowercase() ?: UNKNOWN_STRING,
            locationDto.type?.lowercase() ?: UNKNOWN_STRING,
            locationDto.dimension?.lowercase() ?: UNKNOWN_STRING,
            locationDto.characters
        )
    }


    fun mapCharacterDbModelToEntity(characterDb: CharacterInfoDbModel): CharacterInfo {
        return CharacterInfo(
            characterDb.id,
            characterDb.name,
            characterDb.imageSrc,
            characterDb.imageUrl,
            characterDb.status,
            characterDb.gender,
            characterDb.species,
            characterDb.episodeList?: listOf(),
            characterDb.currentLocationName,
            characterDb.originLocationName

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

    fun mapURLtoId(url: String): Int {
        val result = url.split("/").last()
        return if (isNumeric(result)) {
            result.toInt()
        } else {
            -1
        }
    }

    private fun isNumeric(s: String): Boolean {
        return s.chars().allMatch { char ->
            Character.isDigit(char)
        }
    }
}