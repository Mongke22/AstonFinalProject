package com.example.astonfinalproject.network.Model.Locations

import com.example.astonfinalproject.network.Model.Episodes.EpisodesResultDto
import com.example.astonfinalproject.network.Model.InfoDto
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class LocationsPageResultDto (
    @SerializedName("info")
    @Expose
    val info: InfoDto? = null,
    @SerializedName("results")
    @Expose
    val results: List<LocationsResultDto>? = null
)
