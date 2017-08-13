package com.obaied.dingerquotes.ui.quote

import com.obaied.dingerquotes.data.DataManager
import com.obaied.dingerquotes.data.model.RandomImage
import com.obaied.dingerquotes.ui.base.BasePresenter
import com.obaied.dingerquotes.util.Schedulers.SchedulerProvider
import com.obaied.dingerquotes.util.d
import io.reactivex.disposables.CompositeDisposable
import retrofit2.HttpException
import java.net.SocketTimeoutException
import javax.inject.Inject

/**
 * Created by ab on 19/03/2017.
 */

class QuotePresenter
@Inject constructor(dataManager: DataManager,
                    compositeDisposable: CompositeDisposable,
                    schedulerProvider: SchedulerProvider)
    : BasePresenter<QuoteMvpView>(dataManager, compositeDisposable, schedulerProvider) {

    fun fetchRandomImage() {
        d { "fetchRandomImage(): " }
        checkViewAttached()

        mCompositeDisposable.add(mDataManager.fetchRandomImage()
                .subscribeOn(mSchedulerProvider.io())
                .observeOn(mSchedulerProvider.ui())
                .subscribe({ t: RandomImage? ->
                    if (t == null
                            || t.url.isNullOrEmpty()) {
                        return@subscribe
                    }

                    mvpView?.showImage(t.url)
                }, { t: Throwable ->

                    var error: String = "error "
                    if (t is SocketTimeoutException) {
                        error += " | Timed out | "
                    }
                    error += t.message

                    mvpView?.showError(error)
                }))
    }
}