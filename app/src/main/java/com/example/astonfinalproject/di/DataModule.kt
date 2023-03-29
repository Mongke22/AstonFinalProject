package com.example.astonfinalproject.di

import android.app.Application
import com.example.astonfinalproject.data.LogicRepositoryImpl
import com.example.astonfinalproject.data.database.AppDatabase
import com.example.astonfinalproject.data.database.dao.CharacterInfoDao
import com.example.astonfinalproject.data.database.dao.DataStateDao
import com.example.astonfinalproject.data.database.dao.EpisodeInfoDao
import com.example.astonfinalproject.data.database.dao.LocationInfoDao
import com.example.astonfinalproject.domain.LogicRepository
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface DataModule {

    @Binds
    fun bindLogicRepository(impl: LogicRepositoryImpl): LogicRepository

    companion object{

        @Provides
        fun provideCharacterInfoDao(application: Application): CharacterInfoDao{
            return AppDatabase.getInstance(application).characterInfoDao()
        }
        @Provides
        fun provideEpisodeInfoDao(application: Application): EpisodeInfoDao {
            return AppDatabase.getInstance(application).episodeInfoDao()
        }
        @Provides
        fun provideLocationInfoDao(application: Application): LocationInfoDao {
            return AppDatabase.getInstance(application).locationInfoDao()
        }
        @Provides
        fun provideDataStateDao(application: Application): DataStateDao {
            return AppDatabase.getInstance(application).dataStateDao()
        }
    }
}