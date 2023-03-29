package com.example.astonfinalproject.presentation

import android.app.Application
import com.example.astonfinalproject.di.DaggerApplicationComponent

class AstonApp: Application() {

    val component by lazy{
        DaggerApplicationComponent.factory().create(this)
    }
}