package com.example.astonfinalproject.di

import android.app.Application
import com.example.astonfinalproject.presentation.MainActivity
import com.example.astonfinalproject.presentation.fragments.CharacterFragment
import dagger.BindsInstance
import dagger.Component

@Component(
    modules = [
        DataModule::class,
        ViewModelModule::class
    ]
)
interface ApplicationComponent {

    fun inject(activity: MainActivity)

    fun inject(fragment: CharacterFragment)

    @Component.Factory
    interface Factory{

        fun create(
            @BindsInstance application: Application
        ): ApplicationComponent
    }
}