package com.example.astonfinalproject.data.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.astonfinalproject.data.database.dbModels.CharacterInfoDbModel

@Dao
interface CharacterInfoDao {
    @Query("SELECT * FROM characters")
    fun getCharacterInfoList(): LiveData<List<CharacterInfoDbModel>>

    @Query("SELECT * FROM characters WHERE apiRequest == :characterRequest LIMIT 1")
    fun getCharacterInfo(characterRequest: String): CharacterInfoDbModel

    @Query("SELECT COUNT(*) FROM characters where apiRequest == :characterRequest")
    suspend fun checkCharacterExists(characterRequest: String): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCharacterInfo(character: CharacterInfoDbModel)
}