package com.obaied.dingerquotes.ui.start

import com.obaied.dingerquotes.data.DataManager
import com.obaied.dingerquotes.data.model.Quote
//import com.obaied.dingerquotes.injection.ConfigPersistent
import com.obaied.dingerquotes.ui.base.BasePresenter
import com.obaied.dingerquotes.util.Schedulers.SchedulerProvider
import com.obaied.dingerquotes.util.d
import com.obaied.dingerquotes.util.e
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException
import java.net.SocketTimeoutException
import javax.inject.Inject

/**
 * Created by ab on 19/03/2017.
 */

class StartPresenter
@Inject constructor(dataManager: DataManager,
                    compositeDisposable: CompositeDisposable,
                    schedulerProvider: SchedulerProvider)
    : BasePresenter<StartMvpView>(dataManager, compositeDisposable, schedulerProvider) {

    fun subscribeToDbToFetchQuotes(limit: Int) {
        d { "subscribeToDbToFetchQuotes(): " }

        checkViewAttached()
        mvpView?.showProgress()

        mCompositeDisposable.add(mDataManager.fetchQuotesFromDb(limit)
                .subscribeOn(mSchedulerProvider.io())
                .observeOn(mSchedulerProvider.ui())
                .subscribe(Consumer<List<Quote>> {
                    d { "subscribeToDbToFetchQuotes(): Received quotes: ${it.size}" }
                    mvpView?.hideProgress()

                    if (it.isEmpty()) {
                        mvpView?.showEmpty()
                        return@Consumer
                    }

                    mvpView?.showQuotes(it)

                }, Consumer<Throwable> {
                    e(it, { "subscribeToDbToFetchQuotes(): Received error" })

                    var error: String = "error"
                    if (it is SocketTimeoutException) {
                        error = " | Timed out | "
                    }

                    error += it.message

                    mvpView?.hideProgress()
                    mvpView?.showError(error)
                }
                ))
    }

    fun fetchQuotesFromApi(limit: Int) {
        //since subscribeToDbToFetchQuotes is subscribed to SqlBrite's SELECT statement,
        // whatever I push here would be updated there
        mDataManager.fetchQuotesFromApi(limit)
                .subscribeOn(mSchedulerProvider.io())
                .subscribeBy(
                        onNext = { },
                        onError = { d { "Error occurred: ${it.printStackTrace()}" } },
                        onComplete = { d { "done server fetch" } }
                )
    }
}
