package com.example.astonfinalproject.di

import android.app.Application
import com.example.astonfinalproject.presentation.MainActivity
import com.example.astonfinalproject.presentation.fragments.*
import dagger.BindsInstance
import dagger.Component

@Component(
    modules = [
        DataModule::class,
        ViewModelModule::class,
        PresentationModule::class
    ]
)
interface ApplicationComponent {

    fun inject(activity: MainActivity)

    fun inject(fragment: CharacterFragment)

    fun inject(fragment: EpisodeFragment)

    fun inject(fragment: LocationFragment)

    fun inject(fragment: SingleEpisodeFragment)

    fun inject(fragment: SingleLocationFragment)

    fun inject(fragment: SingleCharacterFragment)

    @Component.Factory
    interface Factory {

        fun create(
            @BindsInstance application: Application,
        ): ApplicationComponent
    }
}