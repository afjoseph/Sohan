package com.obaied.dingerquotes.ui.quote

import com.obaied.dingerquotes.data.DataManager
import com.obaied.dingerquotes.data.model.Quote
import com.obaied.dingerquotes.ui.base.BasePresenter
import com.obaied.dingerquotes.util.Schedulers.AppSchedulerProvider
import com.obaied.dingerquotes.util.Schedulers.SchedulerProvider
import com.obaied.dingerquotes.util.d
import com.obaied.dingerquotes.util.e
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by ab on 19/03/2017.
 */

class QuotePresenter
@Inject constructor(dataManager: DataManager,
                    compositeDisposable: CompositeDisposable,
                    schedulerProvider: SchedulerProvider)
    : BasePresenter<QuoteMvpView>(dataManager, compositeDisposable, schedulerProvider) {

    fun getQuote() {
        d { "getQuote(): " }

        checkViewAttached()

        mCompositeDisposable.add(mDataManager.getQuote()
                .subscribeOn(mSchedulerProvider.io())
                .observeOn(mSchedulerProvider.ui())
                .subscribe(Consumer<Quote> {
                    d { "getQuote(): Received new Quote" }
                    d { "getQuote(): author: [${it.author}], text: [${it.text}]" }

                    if (!isViewAttached) {
                        return@Consumer
                    }

                    mvpView?.showQuote(it)

                }, Consumer<Throwable> {
                    e(it, { "getQuote(): Received error" })

                    if (!isViewAttached) {
                        return@Consumer
                    }

                    mvpView?.showError()
                }
                ))
    }
}