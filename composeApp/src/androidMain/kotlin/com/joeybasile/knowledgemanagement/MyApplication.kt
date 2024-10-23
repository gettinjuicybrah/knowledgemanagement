package com.joeybasile.knowledgemanagement

import android.app.Application
import com.joeybasile.knowledgemanagement.di.initKoin
import org.koin.android.ext.koin.androidContext

class MyApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin{
            androidContext(this@MyApplication)
        }
    }
}