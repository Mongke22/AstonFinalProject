package com.example.astonfinalproject.data.network.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class InfoDto(
    @SerializedName("count")
    @Expose
    val count: Int,
    @SerializedName("pages")
    @Expose
    val pages: Int,
    @SerializedName("next")
    @Expose
    val next: String? = null,
    @SerializedName("prev")
    @Expose
    val prev: String? = null
)

