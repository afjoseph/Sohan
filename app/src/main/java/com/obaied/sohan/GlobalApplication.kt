package com.joseph.sohan

import android.app.Application
import com.joseph.sohan.injection.component.ApplicationComponent
import com.joseph.sohan.injection.component.DaggerApplicationComponent
import com.joseph.sohan.injection.module.ApplicationModule
import timber.log.Timber
import android.os.StrictMode

/**
 * Created by ab on 02/04/2017.
 */

class GlobalApplication : Application() {
    companion object {
        lateinit var globalApp: GlobalApplication
    }

    internal var mApplicationComponent: ApplicationComponent? = null
        get

    override fun onCreate() {
        super.onCreate()
        globalApp = this

        //Init Timber
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        //Init application component
        mApplicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(ApplicationModule(this))
                .build()

        mApplicationComponent?.inject(this)

        //Ignore URI exposure
        val builder = StrictMode.VmPolicy.Builder()
        StrictMode.setVmPolicy(builder.build())
    }
}

