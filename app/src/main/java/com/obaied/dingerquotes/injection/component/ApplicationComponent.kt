package com.obaied.dingerquotes.injection.component

import android.app.Application
import android.content.Context
import com.obaied.dingerquotes.GlobalApplication
import com.obaied.dingerquotes.data.DataManager
import com.obaied.dingerquotes.data.local.DatabaseHelper
import com.obaied.dingerquotes.data.remote.QuoteService
import com.obaied.dingerquotes.data.remote.ServicesHelper
import dagger.Component
import com.obaied.dingerquotes.injection.ForApplication
import com.obaied.dingerquotes.injection.module.ApplicationModule
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
