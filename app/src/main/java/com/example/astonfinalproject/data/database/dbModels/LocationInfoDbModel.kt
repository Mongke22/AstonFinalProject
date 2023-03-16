package com.example.astonfinalproject.data.database.dbModels

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.astonfinalproject.data.database.StringListConverter

@Entity(tableName = "locations")
data class LocationInfoDbModel (
    @PrimaryKey
    val apiRequest: String,
    val id: Int,
    val name: String,
    val type: String,
    val dimension: String,
    val residents: List<String>?
)
