package com.example.astonfinalproject.data.network.Model.Characters

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CharactersResultDto(
    @SerializedName("id")
    @Expose
    val id: Int,
    @SerializedName("name")
    @Expose
    val name: String? = null,
    @SerializedName("status")
    @Expose
    val status: String? = null,
    @SerializedName("species")
    @Expose
    val species: String? = null,
    @SerializedName("type")
    @Expose
    val type: String? = null,
    @SerializedName("gender")
    @Expose
    val gender: String? = null,
    @SerializedName("origin")
    @Expose
    val origin: OriginDto? = null,
    @SerializedName("location")
    @Expose
    val location: LocationDto? = null,
    @SerializedName("episode")
    @Expose
    val episode: List<String>? = null,
    @SerializedName("url")
    @Expose
    val url: String? = null,
    @SerializedName("created")
    @Expose
    val created: String? = null
)
