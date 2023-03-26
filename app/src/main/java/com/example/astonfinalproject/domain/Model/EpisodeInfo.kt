package com.example.astonfinalproject.domain.Model

data class EpisodeInfo (
    override val id: Int,
    val name: String,
    val number: String,
    val date: String,
    val characters: List<String>
): BaseDataInfo(id)