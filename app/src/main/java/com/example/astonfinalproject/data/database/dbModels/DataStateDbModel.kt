package com.example.astonfinalproject.data.database.dbModels

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "states")
data class DataStateDbModel (
    @PrimaryKey
    var screen: String,
    var dataIsReady: Boolean
)