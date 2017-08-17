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
import java.net.SocketTimeoutException
import java.util.*
import javax.inject.Inject

/**
 * Created by ab on 19/03/2017.
 */

class StartPresenter
@Inject constructor(dataManager: DataManager,
                    compositeDisposable: CompositeDisposable,
                    schedulerProvider: SchedulerProvider)
    : BasePresenter<StartMvpView>(dataManager, compositeDisposable, schedulerProvider) {
    fun subscribeToDbToFetchQuotes() {
        d { "subscribeToDbToFetchQuotes(): " }

        checkViewAttached()
        mvpView?.showProgress()

        mCompositeDisposable.add(mDataManager.fetchQuotesFromDb()
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

//        mCompositeDisposable.add(mDataManager.fetchRandomImagesFromApi(limit)
//                .subscribeOn(mSchedulerProvider.io())
//                .subscribeBy(
//                        onNext = { d { "received new quote: $it" } },
//                        onError = { d { "Error occurred: ${it.printStackTrace()}" } },
//                        onComplete = { d { "done server update" } }
//                ))

//                .subscribe(Consumer<List<RandomImage>> { images ->
//                    d { "subscribeToDbToFetchQuotes(): Received images: ${images.size}" }
//
//                    if (images.isEmpty()) {
//                        return@Consumer
//                    }
//
//                    for (image in images) {
//                        d { "quote's image url: ${image.url}" }
//                    }
//
//                    //TODO: Assing an image_url to each quote
//                    //TODO: then, simply use the image_url from the quote to the RecyclerView and the
//                    // QuoteActivity.
//
//                    (0 until quotes.size)
//                            .filter { quotes[it].imageTag == null }
//                            .filter { it < images.size }
//                            .forEach { quotes[it].imageTag = images[it].url }
//
////                    mvpView?.showQuotes(quotes)
//
//                }, Consumer<Throwable> {
//                    e(it, { "subscribeToDbToFetchQuotes(): Received error" })
//
//                    var error: String = "error"
//                    if (it is SocketTimeoutException) {
//                        error = " | Timed out | "
//                    }
//
//                    error += it.message
//
//                    mvpView?.hideProgress()
//                    mvpView?.showError(error)
//                }
//                ))

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
