package com.obaied.sohan.ui.start

import com.obaied.sohan.data.model.Quote
import com.obaied.sohan.ui.base.MvpView

/**
 * Created by ab on 19/03/2017.
 */

interface StartMvpView : MvpView {
    fun showQuotes(quotes: List<Quote>)
    fun showEmpty()
    fun showError(error: String)
    fun showProgress()
    fun hideProgress()
}
