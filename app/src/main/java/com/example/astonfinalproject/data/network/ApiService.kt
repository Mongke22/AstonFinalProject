package com.example.astonfinalproject.data.network

import com.example.astonfinalproject.data.network.Model.Characters.CharactersPageResultDto
import com.example.astonfinalproject.data.network.Model.Characters.CharactersResultDto
import com.example.astonfinalproject.data.network.Model.Characters.LocationDto
import com.example.astonfinalproject.data.network.Model.Episodes.EpisodesPageResultDto
import com.example.astonfinalproject.data.network.Model.Episodes.EpisodesResultDto
import com.example.astonfinalproject.data.network.Model.Locations.LocationsPageResultDto
import com.example.astonfinalproject.data.network.Model.Locations.LocationsResultDto
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("character")
    fun getCharacters(
        @Query(QUERY_PARAM_PAGE) page: Int = FIRST_PAGE,
    ): Single<CharactersPageResultDto>

    @GET("episode")
    fun getEpisodes(
        @Query(QUERY_PARAM_PAGE) page: Int = FIRST_PAGE,
    ): Single<EpisodesPageResultDto>

    @GET("location")
    fun getLocations(
        @Query(QUERY_PARAM_PAGE) page: Int = FIRST_PAGE,
    ): Single<LocationsPageResultDto>

    @GET("character/{characterId}")
    fun getCharacter(
        @Path("characterId") charId: Int
    ): Single<CharactersResultDto>

    @GET("episode/{episodeId}")
    fun getEpisode(
        @Path("episodeId") charId: Int
    ): Single<EpisodesResultDto>

    @GET("location/{locationId}")
    fun getLocation(
        @Path("locationId") charId: Int
    ): Single<LocationsResultDto>

    companion object {
        private const val QUERY_PARAM_PAGE = "page"

        private const val FIRST_PAGE = 1
    }
}