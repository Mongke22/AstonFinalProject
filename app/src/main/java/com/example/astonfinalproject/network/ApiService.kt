package com.example.astonfinalproject.network

import com.example.astonfinalproject.network.Model.Characters.CharactersPageResultDto
import com.example.astonfinalproject.network.Model.Episodes.EpisodesPageResultDto
import io.reactivex.Single
import retrofit2.http.GET
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
    ): Single<CharactersPageResultDto>

    companion object {
        private const val QUERY_PARAM_PAGE = "page"

        private const val FIRST_PAGE = 1
    }
}