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

    @Query("SELECT * FROM locations WHERE apiRequest == :locationRequest LIMIT 1")
    fun getLocationInfo(locationRequest: String): LocationInfoDbModel

    @Query("SELECT COUNT(*) FROM locations where apiRequest == :locationRequest")
    suspend fun checkLocationExists(locationRequest: String): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLocationInfo(location: LocationInfoDbModel)
}