package com.obaied.dingerquotes.ui.base

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.obaied.dingerquotes.GlobalApplication
import com.obaied.dingerquotes.injection.component.ActivityComponent
import com.obaied.dingerquotes.injection.component.DaggerActivityComponent
import com.obaied.dingerquotes.injection.module.ActivityModule

/**
 * Created by ab on 19/03/2017.
 */

open class BaseActivity : AppCompatActivity() {
    private var mActivityComponent: ActivityComponent? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mActivityComponent = DaggerActivityComponent.builder()
                .activityModule(ActivityModule(this))
                .applicationComponent(GlobalApplication.get(this).getComponent())
                .build()
    }

    fun activityComponent(): ActivityComponent? {
        return mActivityComponent
    }
}
