package com.obaied.dingerquotes.injection.module

import android.app.*
import android.content.*
import com.obaied.dingerquotes.data.remote.QuoteService
import com.obaied.dingerquotes.injection.ForApplication

import javax.inject.*

import dagger.*

/**
 * Created by ab on 18/03/2017.
 */

@Module
class ApplicationModule(protected val mApplication: Application) {

    @Provides
    @Singleton
    internal fun provideApplication(): Application {
        return mApplication
    }

    @Provides
    @ForApplication
    internal fun provideContext(): Context {
        return mApplication
    }

    @Provides
    @Singleton
    internal fun provideQuoteService(): QuoteService {
        return QuoteService.Creator.newQuoteService()
    }

}

