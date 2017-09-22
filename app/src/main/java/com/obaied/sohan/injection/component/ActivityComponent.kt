package com.obaied.sohan.injection.component

import com.obaied.sohan.injection.PerActivity
import com.obaied.sohan.injection.module.ActivityModule
import com.obaied.sohan.ui.quote.QuoteActivity
import com.obaied.sohan.ui.start.StartActivity
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