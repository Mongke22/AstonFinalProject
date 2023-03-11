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

    @Query("SELECT * FROM episodes WHERE apiRequest == :episodeRequest LIMIT 1")
    fun getEpisodeInfo(episodeRequest: String): EpisodeInfoDbModel

    @Query("SELECT COUNT(*) FROM episodes where apiRequest == :episodeRequest")
    suspend fun checkEpisodeExists(episodeRequest: String): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEpisodeInfo(episode: EpisodeInfoDbModel)
}