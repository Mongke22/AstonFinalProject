package com.example.astonfinalproject.data.network.Model.Characters

import com.example.astonfinalproject.data.network.Model.InfoDto
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
