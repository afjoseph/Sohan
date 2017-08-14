package com.obaied.dingerquotes

import android.app.Application
import com.obaied.dingerquotes.injection.component.ApplicationComponent
import com.obaied.dingerquotes.injection.component.DaggerApplicationComponent
import com.obaied.dingerquotes.injection.module.ApplicationModule
import timber.log.Timber
import javax.inject.Inject
import android.os.StrictMode

/**
 * Created by ab on 02/04/2017.
 */

class GlobalApplication : Application() {
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

//        JobManager.create(this)
//                .addJobCreator(jobCreator)

        //Ignore URI exposure
        val builder = StrictMode.VmPolicy.Builder()
        StrictMode.setVmPolicy(builder.build())
    }

    companion object {
        lateinit var globalApp: GlobalApplication
    }
}

