package com.example.astonfinalproject.data.database.dbModels

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.astonfinalproject.data.database.StringListConverter

@Entity(tableName = "locations")
data class LocationInfoDbModel (
    @PrimaryKey
    val apiRequest: String,
    val name: String,
    val type: String,
    val dimension: String,
    @TypeConverters(StringListConverter::class)
    val residents: List<String>?
)
