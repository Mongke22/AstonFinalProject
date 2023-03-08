package com.example.astonfinalproject.network.Model.Characters

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class LocationDto(
    @SerializedName("name")
    @Expose
    val name: String? = null,
    @SerializedName("url")
    @Expose
    val url: String? = null
)
