package com.joseph.sohan.injection.module

import android.app.*
import android.content.*
import com.joseph.sohan.data.remote.QuoteService
import com.joseph.sohan.injection.ForApplication
import com.joseph.sohan.util.Schedulers.AppSchedulerProvider
import com.joseph.sohan.util.Schedulers.SchedulerProvider

import javax.inject.*

import dagger.*

/**
 * Created by ab on 18/03/2017.
 */

@Module
class ApplicationModule(protected val application: Application) {
    @Provides
    @Singleton
    internal fun provideApplication(): Application {
        return application
    }

    @Provides
    @ForApplication
    internal fun provideContext(): Context {
        return application
    }

    @Provides
    @Singleton
    internal fun provideQuoteService(): QuoteService {
        return QuoteService.Builder.newService()
    }

    @Provides
    internal fun providesSchedulerProvider(): SchedulerProvider {
        return AppSchedulerProvider()
    }
}

