package com.example.astonfinalproject.data.database.mapper

import android.app.Application
import com.example.astonfinalproject.R
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
import javax.inject.Inject

class Mapper @Inject constructor(val application: Application){

    fun mapCharacterDtoToDbModel(characterDto: CharactersResultDto): CharacterInfoDbModel {
        return CharacterInfoDbModel(
            characterDto.url?.lowercase() ?: application.getString(R.string.string_unknown),
            characterDto.id,
            characterDto.name ?: application.getString(R.string.string_unknown),
            characterDto.species?.lowercase() ?: application.getString(R.string.string_unknown),
            characterDto.status?.lowercase() ?: application.getString(R.string.string_unknown),
            characterDto.gender?.lowercase() ?: application.getString(R.string.string_unknown),
            application.getString(R.string.string_unknown),
            characterDto.image?.lowercase() ?: application.getString(R.string.string_unknown),
            characterDto.origin?.name?.lowercase() ?: application.getString(R.string.string_unknown),
            characterDto.origin?.url?.lowercase() ?: application.getString(R.string.string_unknown),
            characterDto.location?.name?.lowercase() ?: application.getString(R.string.string_unknown),
            characterDto.location?.url?.lowercase() ?: application.getString(R.string.string_unknown),
            characterDto.episode
        )
    }

    fun mapEpisodeDtoToDbModel(episodeDto: EpisodesResultDto): EpisodeInfoDbModel {
        return EpisodeInfoDbModel(
            episodeDto.url?.lowercase() ?: application.getString(R.string.string_unknown),
            episodeDto.id,
            episodeDto.name?.lowercase() ?: application.getString(R.string.string_unknown),
            episodeDto.air_date?.lowercase() ?: application.getString(R.string.string_unknown),
            episodeDto.episode?.lowercase() ?: application.getString(R.string.string_unknown),
            episodeDto.characters
        )
    }

    fun mapLocationDtoToDbModel(locationDto: LocationsResultDto): LocationInfoDbModel {
        return LocationInfoDbModel(
            locationDto.url?.lowercase() ?: application.getString(R.string.string_unknown),
            locationDto.id,
            locationDto.name?.lowercase() ?: application.getString(R.string.string_unknown),
            locationDto.type?.lowercase() ?: application.getString(R.string.string_unknown),
            locationDto.dimension?.lowercase() ?: application.getString(R.string.string_unknown),
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
            episodeDb.airDate,
            episodeDb.characters?: listOf()
        )
    }

    fun mapLocationDbModelToEntity(locationDb: LocationInfoDbModel): LocationInfo {
        return LocationInfo(
            locationDb.id,
            locationDb.name,
            locationDb.type,
            locationDb.dimension,
            locationDb.residents ?: listOf()
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