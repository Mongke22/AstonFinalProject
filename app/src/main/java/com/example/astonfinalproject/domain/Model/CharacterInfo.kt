package com.example.astonfinalproject.domain.Model

data class CharacterInfo (
    override var id: Int,
    var name: String,
    var imgSrc: String,
    var imgUrl: String,
    var status: String,
    var gender: String,
    var species: String,
    var episodes: List<String>,
    var currentLocation: String,
    var origin: String
): BaseDataInfo(id)
