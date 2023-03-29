package com.example.astonfinalproject.di

import com.example.astonfinalproject.presentation.filter.model.CharacterFilter
import com.example.astonfinalproject.presentation.filter.model.EpisodeFilter
import com.example.astonfinalproject.presentation.filter.model.LocationFilter
import dagger.Module
import dagger.Provides


@Module
class PresentationModule {

    @Provides
    fun provideCharacterFilter(): CharacterFilter{
        return CharacterFilter("", "","")
    }

    @Provides
    fun provideEpisodeFilter(): EpisodeFilter{
        return EpisodeFilter("","")
    }

    @Provides
    fun provideLocationFilter(): LocationFilter{
        return LocationFilter("","")
    }
}