package com.example.astonfinalproject.network.Model.Characters

import com.example.astonfinalproject.network.Model.InfoDto
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CharactersPageResultDto(
    @SerializedName("info")
    @Expose
    val info: InfoDto? = null,
    @SerializedName("results")
    @Expose
    val results: List<CharactersResultDto>? = null

)
