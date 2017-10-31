package com.joseph.sohan.injection.component

import android.app.Application
import android.content.Context
import com.joseph.sohan.GlobalApplication
import com.joseph.sohan.data.DataManager
import com.joseph.sohan.data.local.DatabaseHelper
import com.joseph.sohan.data.remote.QuoteService
import com.joseph.sohan.data.remote.ServicesHelper
import dagger.Component
import com.joseph.sohan.injection.ForApplication
import com.joseph.sohan.injection.module.ApplicationModule
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
