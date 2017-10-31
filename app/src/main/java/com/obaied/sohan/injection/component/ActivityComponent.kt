package com.joseph.sohan.injection.component

import com.joseph.sohan.injection.PerActivity
import com.joseph.sohan.injection.module.ActivityModule
import com.joseph.sohan.ui.quote.QuoteActivity
import com.joseph.sohan.ui.start.StartActivity
import dagger.Component

/**
 * Created by ab on 19/03/2017.
 */

@PerActivity
@Component(dependencies = arrayOf(ApplicationComponent::class),
        modules = arrayOf(ActivityModule::class))
interface ActivityComponent {
    fun inject(activity: StartActivity)
    fun inject(activity: QuoteActivity)
}