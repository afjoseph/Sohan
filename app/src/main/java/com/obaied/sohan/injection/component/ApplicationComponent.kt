package com.obaied.sohan.injection.component

import android.app.Application
import android.content.Context
import com.obaied.sohan.GlobalApplication
import com.obaied.sohan.data.DataManager
import com.obaied.sohan.data.local.DatabaseHelper
import com.obaied.sohan.data.remote.QuoteService
import com.obaied.sohan.data.remote.ServicesHelper
import dagger.Component
import com.obaied.sohan.injection.ForApplication
import com.obaied.sohan.injection.module.ApplicationModule
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(ApplicationModule::class))
interface ApplicationComponent {

    fun inject(app: GlobalApplication)

    @ForApplication
    fun context(): Context

    fun application(): Application

    fun dataManager(): DataManager

    fun quoteService(): QuoteService

    fun databaseHelper(): DatabaseHelper

    fun ServiceHelper(): ServicesHelper
}
