package com.joseph.sohan.injection.module

import android.app.Activity
import android.content.Context
import com.joseph.sohan.injection.ActivityContext
import com.joseph.sohan.util.Schedulers.AppSchedulerProvider
import com.joseph.sohan.util.Schedulers.SchedulerProvider
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable

/**
 * Created by ab on 18/03/2017.
 */

@Module
class ActivityModule(private val mActivity: Activity) {
    @Provides
    fun provideActivity(): Activity {
        return mActivity
    }

    @Provides
    @ActivityContext
    fun providesContext(): Context {
        return mActivity
    }

    @Provides
    fun providesCompositeDisposable(): CompositeDisposable {
        return CompositeDisposable()
    }

    @Provides
    fun providesSchedulerProvider(): SchedulerProvider {
        return AppSchedulerProvider()
    }
}