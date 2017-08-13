package com.obaied.dingerquotes.injection.module

import android.app.*
import android.content.*
import com.obaied.dingerquotes.data.remote.QuoteService
import com.obaied.dingerquotes.data.remote.RandomImageService
import com.obaied.dingerquotes.injection.ForApplication
import com.obaied.dingerquotes.util.Schedulers.AppSchedulerProvider
import com.obaied.dingerquotes.util.Schedulers.SchedulerProvider

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
    @Singleton
    internal fun provideRandomImageService(): RandomImageService {
        return RandomImageService.Builder.newService()
    }

    @Provides
    internal fun providesSchedulerProvider(): SchedulerProvider {
        return AppSchedulerProvider()
    }
}

