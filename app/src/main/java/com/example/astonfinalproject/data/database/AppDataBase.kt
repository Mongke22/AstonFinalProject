package com.example.astonfinalproject.data.database

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.astonfinalproject.data.database.dao.CharacterInfoDao
import com.example.astonfinalproject.data.database.dao.EpisodeInfoDao
import com.example.astonfinalproject.data.database.dao.LocationInfoDao
import com.example.astonfinalproject.data.database.dbModels.CharacterInfoDbModel
import com.example.astonfinalproject.data.database.dbModels.EpisodeInfoDbModel
import com.example.astonfinalproject.data.database.dbModels.LocationInfoDbModel

@Database(
    entities = [CharacterInfoDbModel::class, EpisodeInfoDbModel::class, LocationInfoDbModel::class],
    version = 7, exportSchema = false
)
@TypeConverters(StringListConverter::class)
abstract class AppDatabase : RoomDatabase() {
    companion object {

        private var INSTANCE: AppDatabase? = null
        private const val DB_NAME = "rick_morty.db"
        private val LOCK = Any()

        fun getInstance(application: Application): AppDatabase {
            INSTANCE?.let {
                return it
            }
            synchronized(LOCK) {
                INSTANCE?.let { return it }
                val instance =
                    Room.databaseBuilder(
                        application,
                        AppDatabase::class.java,
                        DB_NAME
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                INSTANCE = instance
                return instance
            }
        }
    }

    abstract fun characterInfoDao(): CharacterInfoDao
    abstract fun episodeInfoDao(): EpisodeInfoDao
    abstract fun locationInfoDao(): LocationInfoDao
}