package com.sbaygildin.sequeniafilmexplorer

import android.app.Application
import com.sbaygildin.sequeniafilmexplorer.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()


        startKoin {
            androidContext(this@MyApplication)
            modules(appModule)
        }

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}