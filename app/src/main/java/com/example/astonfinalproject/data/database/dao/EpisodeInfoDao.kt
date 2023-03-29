package com.example.astonfinalproject.data.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.astonfinalproject.data.database.dbModels.CharacterInfoDbModel
import com.example.astonfinalproject.data.database.dbModels.EpisodeInfoDbModel

@Dao
interface EpisodeInfoDao {
    @Query("SELECT * FROM episodes")
    fun getEpisodeInfoList(): LiveData<List<EpisodeInfoDbModel>>

    @Query("SELECT * FROM episodes WHERE id IN (:ids)")
    suspend fun getSelectedEpisodeInfoList(ids: List<Int>): List<EpisodeInfoDbModel>

    @Query("SELECT * FROM episodes WHERE id == :getId LIMIT 1")
    suspend fun getEpisodeInfo(getId: Int): EpisodeInfoDbModel

    @Query("SELECT COUNT(*) FROM episodes where id == :getId")
    fun checkEpisodeExists(getId: Int): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertEpisodeInfo(episode: EpisodeInfoDbModel)
}