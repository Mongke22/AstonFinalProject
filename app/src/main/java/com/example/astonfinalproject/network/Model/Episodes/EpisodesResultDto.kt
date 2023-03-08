package com.example.astonfinalproject.network.Model.Episodes

import com.example.astonfinalproject.network.Model.Characters.LocationDto
import com.example.astonfinalproject.network.Model.Characters.OriginDto
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class EpisodesResultDto (
    @SerializedName("id")
    @Expose
    val id: Int,
    @SerializedName("name")
    @Expose
    val name: String? = null,
    @SerializedName("air_date")
    @Expose
    val air_date: String? = null,
    @SerializedName("episode")
    @Expose
    val episode: String? = null,
    @SerializedName("characters")
    @Expose
    val characters: List<String>? = null,
    @SerializedName("url")
    @Expose
    val url: String? = null,
    @SerializedName("created")
    @Expose
    val created: String? = null
)
