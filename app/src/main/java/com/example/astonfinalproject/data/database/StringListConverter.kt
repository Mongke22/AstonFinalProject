package com.example.astonfinalproject.data.database

import androidx.room.TypeConverter
import java.util.stream.Collectors

class StringListConverter {

    @TypeConverter
    fun fromListToString(list: List<String>): String {
        return list.stream().collect(Collectors.joining(","))
    }

    @TypeConverter
    fun toStringToList(data: String): List<String> {
        return data.split(",")
    }
}