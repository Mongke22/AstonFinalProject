package com.example.astonfinalproject.data.database.dbModels

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.astonfinalproject.data.database.StringListConverter


@Entity(tableName = "characters")
data class CharacterInfoDbModel(
    @PrimaryKey
    val apiRequest: String,
    val id: Int,
    val name: String,
    val species: String,
    val status: String,
    val gender: String,
    val imageSrc: String,
    val originLocationName: String,
    val originLocationApiRequest: String,
    val currentLocationName: String,
    val currentLocationApiRequest: String,
    val episodeList: List<String>?

)
