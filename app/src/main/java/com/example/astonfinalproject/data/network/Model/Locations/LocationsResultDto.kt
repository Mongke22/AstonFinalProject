package com.example.astonfinalproject.data.network.Model.Locations

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class LocationsResultDto (
    @SerializedName("id")
    @Expose
    val id: Int,
    @SerializedName("name")
    @Expose
    val name: String? = null,
    @SerializedName("type")
    @Expose
    val type: String? = null,
    @SerializedName("dimension")
    @Expose
    val dimension: String? = null,
    @SerializedName("residents")
    @Expose
    val characters: List<String>? = null,
    @SerializedName("url")
    @Expose
    val url: String? = null,
    @SerializedName("created")
    @Expose
    val created: String? = null
)
