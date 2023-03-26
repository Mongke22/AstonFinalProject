package com.example.astonfinalproject.data.database.dbModels

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.astonfinalproject.data.database.StringListConverter


@Entity(tableName = "characters")
data class CharacterInfoDbModel(
    @PrimaryKey
    var apiRequest: String,
    var id: Int,
    var name: String,
    var species: String,
    var status: String,
    var gender: String,
    var imageSrc: String,
    var imageUrl: String,
    var originLocationName: String,
    var originLocationApiRequest: String,
    var currentLocationName: String,
    var currentLocationApiRequest: String,
    var episodeList: List<String>?

)
