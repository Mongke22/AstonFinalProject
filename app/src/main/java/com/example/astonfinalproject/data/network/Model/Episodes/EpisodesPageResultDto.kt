package com.example.astonfinalproject.data.network.Model.Episodes

import com.example.astonfinalproject.data.network.Model.Characters.CharactersResultDto
import com.example.astonfinalproject.data.network.Model.InfoDto
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class EpisodesPageResultDto(
    @SerializedName("info")
    @Expose
    val info: InfoDto? = null,
    @SerializedName("results")
    @Expose
    val results: List<EpisodesResultDto>? = null

)