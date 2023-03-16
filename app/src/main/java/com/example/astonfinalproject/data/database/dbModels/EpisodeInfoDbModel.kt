package com.example.astonfinalproject.data.database.dbModels

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.astonfinalproject.data.database.StringListConverter

@Entity(tableName = "episodes")
data class EpisodeInfoDbModel (
    @PrimaryKey
    val apiRequest: String,
    val id: Int,
    val name: String,
    val airDate: String,
    val episodeNumber: String,
    val characters: List<String>?
)