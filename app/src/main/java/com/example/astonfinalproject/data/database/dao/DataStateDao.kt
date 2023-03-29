package com.example.astonfinalproject.data.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.astonfinalproject.data.database.dbModels.DataStateDbModel
import com.example.astonfinalproject.data.database.dbModels.LocationInfoDbModel

@Dao
interface DataStateDao {

    @Query("SELECT * FROM episodes")
    fun getDataStateList(): LiveData<List<DataStateDbModel>>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDataState(state: DataStateDbModel)
}