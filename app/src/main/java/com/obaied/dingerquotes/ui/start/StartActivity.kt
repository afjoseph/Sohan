package com.obaied.dingerquotes.ui.start

import android.content.Intent
import android.os.Bundle
import com.obaied.dingerquotes.R
import com.obaied.dingerquotes.ui.base.BaseActivity
import com.obaied.dingerquotes.ui.quote.QuoteActivity
import com.obaied.dingerquotes.util.d
import kotlinx.android.synthetic.main.activity_start.*
import javax.inject.Inject

class StartActivity : BaseActivity(), StartMvpView {
    @Inject lateinit var mPresenter: StartPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        d { "startActivity: onCreate() " }

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)


        activityComponent()?.inject(this)
        mPresenter.attachView(this)

        if (savedInstanceState != null) {
            return
        }

        start_button_run.setOnClickListener {
            d { "start_button_run: onClickListener" }

            val intent = Intent(this, QuoteActivity::class.java)
            startActivity(intent)
        }
    }
}
