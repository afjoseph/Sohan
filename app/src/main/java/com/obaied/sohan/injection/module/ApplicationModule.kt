package com.obaied.sohan.injection.module

import android.app.*
import android.content.*
import com.obaied.sohan.data.remote.QuoteService
import com.obaied.sohan.injection.ForApplication
import com.obaied.sohan.util.Schedulers.AppSchedulerProvider
import com.obaied.sohan.util.Schedulers.SchedulerProvider

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

