package com.example.astonfinalproject.data.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.astonfinalproject.data.database.dbModels.CharacterInfoDbModel
import com.example.astonfinalproject.data.database.dbModels.EpisodeInfoDbModel

@Dao
interface CharacterInfoDao {
    @Query("SELECT * FROM characters")
    fun getCharacterInfoList(): LiveData<List<CharacterInfoDbModel>>

    @Query("SELECT * FROM characters WHERE id == :getId LIMIT 1")
    fun getCharacterInfo(getId: Int): CharacterInfoDbModel

    @Query("SELECT COUNT(*) FROM characters where id == :getId")
    fun checkCharacterExists(getId: Int): Int

    @Query("UPDATE characters SET imageSrc = :imgSrc WHERE id == :theId")
    suspend fun updateImagePath(imgSrc: String, theId: Int): Int

    @Query("SELECT * FROM characters WHERE id IN (:ids)")
    suspend fun getSelectedCharacterInfoList(ids: List<Int>): List<CharacterInfoDbModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCharacterInfo(character: CharacterInfoDbModel)
}