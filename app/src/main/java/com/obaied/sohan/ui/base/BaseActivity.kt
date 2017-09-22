package com.obaied.sohan.ui.base

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.obaied.sohan.GlobalApplication
import com.obaied.sohan.injection.component.ActivityComponent
import com.obaied.sohan.injection.component.DaggerActivityComponent
import com.obaied.sohan.injection.module.ActivityModule

/**
 * Created by ab on 19/03/2017.
 */

open class BaseActivity : AppCompatActivity() {
    private var mActivityComponent: ActivityComponent? = null
    private var isAttachToWindow = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mActivityComponent = DaggerActivityComponent.builder()
                .activityModule(ActivityModule(this))
                .applicationComponent(GlobalApplication.globalApp.mApplicationComponent)
                .build()
    }

    fun activityComponent(): ActivityComponent? {
        return mActivityComponent
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()

        isAttachToWindow = true
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()

        isAttachToWindow = false
    }
}
