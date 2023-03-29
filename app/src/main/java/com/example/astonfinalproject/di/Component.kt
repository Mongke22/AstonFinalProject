package com.example.astonfinalproject.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component

@Component(
    modules = [
        DataModule::class
    ]
)
interface Component {

    @Component.Factory
    interface Factory{

        fun create(
            @BindsInstance application: Application
        ): Component
    }
}