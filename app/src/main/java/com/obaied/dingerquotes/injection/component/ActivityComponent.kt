package com.obaied.dingerquotes.injection.component

import dagger.Subcomponent
import com.obaied.dingerquotes.injection.PerActivity
import com.obaied.dingerquotes.injection.module.ActivityModule
import com.obaied.dingerquotes.ui.quote.QuoteActivity
import com.obaied.dingerquotes.ui.start.StartActivity
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