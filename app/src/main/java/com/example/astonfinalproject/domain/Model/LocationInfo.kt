package com.example.astonfinalproject.domain.Model

data class LocationInfo (
    override val id: Int,
    val name: String,
    val type: String,
    val dimension: String,
    val residents: List<String>
): BaseDataInfo(id)