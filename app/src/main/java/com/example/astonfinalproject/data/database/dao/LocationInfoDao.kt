package com.example.astonfinalproject.data.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.astonfinalproject.data.database.dbModels.EpisodeInfoDbModel
import com.example.astonfinalproject.data.database.dbModels.LocationInfoDbModel

@Dao
interface LocationInfoDao {
    @Query("SELECT * FROM locations")
    fun getLocationInfoList(): LiveData<List<LocationInfoDbModel>>

    @Query("SELECT * FROM locations WHERE id == :getId LIMIT 1")
    fun getLocationInfo(getId: Int): LocationInfoDbModel

    @Query("SELECT * FROM locations WHERE name == :placeName LIMIT 1")
    fun getLocationInfo(placeName: String): LocationInfoDbModel

    @Query("SELECT COUNT(*) FROM locations where id == :getId")
    fun checkLocationExists(getId: Int): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertLocationInfo(location: LocationInfoDbModel)
}