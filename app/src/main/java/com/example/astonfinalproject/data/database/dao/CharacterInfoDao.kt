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

    @Query("SELECT * FROM characters WHERE id == :getId LIMIT 1")
    fun getCharacterInfo(getId: Int): CharacterInfoDbModel

    @Query("SELECT COUNT(*) FROM characters where id == :getId")
    fun checkCharacterExists(getId: Int): Int

    @Query("UPDATE characters SET imageSrc = :imgSrc WHERE id == :theId")
    suspend fun updateImagePath(imgSrc: String, theId: Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCharacterInfo(character: CharacterInfoDbModel)
}