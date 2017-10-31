package com.joseph.sohan.ui.start

import com.joseph.sohan.data.DataManager
import com.joseph.sohan.data.model.Quote
import com.joseph.sohan.ui.base.BasePresenter
import com.joseph.sohan.util.Schedulers.SchedulerProvider
import com.joseph.sohan.util.d
import com.joseph.sohan.util.e
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer
import io.reactivex.functions.Function
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

/**
 * Created by ab on 19/03/2017.
 */

class StartPresenter
@Inject constructor(dataManager: DataManager,
                    compositeDisposable: CompositeDisposable,
                    schedulerProvider: SchedulerProvider)
    : BasePresenter<StartMvpView>(dataManager, compositeDisposable, schedulerProvider) {
    val retrySubject = PublishSubject.create<Any>()

    fun subscribeToDbToFetchQuotes() {
        d { "subscribeToDbToFetchQuotes(): " }

        checkViewAttached()
        mvpView?.showProgress()

        mCompositeDisposable.add(mDataManager.fetchQuotesFromDb()
                .subscribeOn(mSchedulerProvider.io())
                .observeOn(mSchedulerProvider.ui())
//                .retryWhen { it.flatMap { retrySubject } }
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

                    mvpView?.hideProgress()
                    mvpView?.showError(it.message!!)
                }
                )
        )
    }

    fun fetchQuotesFromDb() {
        retrySubject.onNext(1)
    }

    fun fetchQuotesFromApi(limit: Int) {
        //since subscribeToDbToFetchQuotes is subscribed to SqlBrite's SELECT statement,
        // whatever I push here would be updated there
        mDataManager.fetchQuotesFromApi(limit)
                .subscribeOn(mSchedulerProvider.io())
                .observeOn(mSchedulerProvider.ui())
                .onErrorResumeNext(Function { Observable.error<List<Quote>>(it) }) //OnErrorResumeNext and Observable.error() would propagate the error to the next level. So, whatever error occurs here, would get passed to onError() on the UI side
                .flatMap { t: List<Quote> ->
                    //Chain observable as such
                    mDataManager.setQuotesToDb(t).subscribe({}, { e { "setQuotesToDb() error occurred: ${it.localizedMessage}" } }, { d { "Done server set" } })
                    Observable.just(t)
                }
                .subscribeBy(
                        onNext = {},
                        onError = { mvpView?.showError("No internet connection") },
                        onComplete = { d { "onComplete(): done with fetching quotes from api" } }
                )
    }
}
