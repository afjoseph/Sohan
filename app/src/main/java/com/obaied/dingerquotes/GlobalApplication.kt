package com.obaied.dingerquotes

import android.app.Application
import android.content.Context
import com.obaied.dingerquotes.injection.component.ApplicationComponent
import com.obaied.dingerquotes.injection.component.DaggerApplicationComponent
import com.obaied.dingerquotes.injection.module.ApplicationModule
import timber.log.Timber

/**
 * Created by ab on 02/04/2017.
 */

class GlobalApplication : Application() {
    internal var mApplicationComponent: ApplicationComponent? = null
        get

    companion object {
        fun get(context: Context): GlobalApplication {
            return context.applicationContext as GlobalApplication
        }
    }

    override fun onCreate() {
        super.onCreate()

        //Init Timber
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        //Init application component
        mApplicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(ApplicationModule(this))
                .build()

        mApplicationComponent?.inject(this)
    }
}

