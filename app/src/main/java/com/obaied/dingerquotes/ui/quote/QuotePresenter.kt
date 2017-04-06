package com.obaied.dingerquotes.ui.quote

import com.obaied.dingerquotes.data.DataManager
import com.obaied.dingerquotes.data.model.Quote
import com.obaied.dingerquotes.ui.base.BasePresenter
import com.obaied.dingerquotes.util.d
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by ab on 19/03/2017.
 */

class QuotePresenter
@Inject constructor(dataManager: DataManager,
                    compositeDisposable: CompositeDisposable)
    : BasePresenter<QuoteMvpView>(dataManager, compositeDisposable) {
    private val TAG = "QuotePresenter"

    fun loadRandomQuote() {
        d { "loadRandomQuote(): " }

        checkViewAttached()

        mCompositeDisposable.add(mDataManager
                .loadRandomQuote().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Consumer<Quote> {
                    d { "loadRandomQuote(): " }
                    d { "loadRandomQuote(): Received new Quote" }
                    d { "loadRandomQuote(): author: [${it.author}], text: [${it.text}]" }

                    if (!isViewAttached) {
                        return@Consumer
                    }

                    mvpView?.onGettingNewQuote(it)

                }, Consumer<Throwable> {
                    Timber.e(it, "loadRandomQuote(): Received error")

                    if (!isViewAttached) {
                        return@Consumer
                    }

                    mvpView?.showError(it)
                }
                ))
    }

}