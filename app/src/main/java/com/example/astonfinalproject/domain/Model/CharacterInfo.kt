package com.example.astonfinalproject.domain.Model

data class CharacterInfo (
    override val id: Int,
    val name: String,
    val imgSrc: String,
    val imgUrl: String,
    val status: String,
    val gender: String,
    val species: String
): BaseDataInfo(id)
