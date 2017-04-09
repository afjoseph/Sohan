package com.obaied.dingerquotes.ui.quote

import android.os.Bundle
import com.obaied.dingerquotes.R
import com.obaied.dingerquotes.data.model.Quote
import com.obaied.dingerquotes.ui.base.BaseActivity
import com.obaied.dingerquotes.util.d
import com.obaied.dingerquotes.util.e
import kotlinx.android.synthetic.main.activity_quote.*
import javax.inject.Inject

class QuoteActivity : BaseActivity(), QuoteMvpView {
    @Inject lateinit var mPresenter: QuotePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quote)

        activityComponent()?.inject(this)
        mPresenter.attachView(this)

        mPresenter.getQuote()

        quote_button_next.setOnClickListener {
            //TODO: Disable next button until quote arrives
            mPresenter.getQuote()
        }
    }

    override fun showQuote(newQuote: Quote) {
        d { "showQuote{): " }

        quote_text_quote.animateText("${newQuote.text} \n--  ${newQuote.author}")
    }

    override fun showError() {
        d { "showError{): " }

        quote_text_quote.text = "Error"
    }
}
