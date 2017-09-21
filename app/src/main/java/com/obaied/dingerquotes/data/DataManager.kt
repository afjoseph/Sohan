package com.obaied.dingerquotes.data

import com.obaied.dingerquotes.data.local.DatabaseHelper
import com.obaied.dingerquotes.data.model.Quote
import com.obaied.dingerquotes.data.remote.QuoteService
import com.obaied.dingerquotes.data.remote.ServicesHelper
import com.obaied.dingerquotes.util.d
import com.obaied.dingerquotes.util.e
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.functions.Function
import io.reactivex.rxkotlin.subscribeBy
import java.io.IOException
import java.net.SocketTimeoutException
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by ab on 02/04/2017.
 */

@Singleton
class DataManager
@Inject constructor(val quoteService: QuoteService,
                    val databaseHelper: DatabaseHelper,
                    val servicesHelper: ServicesHelper) {
    private val random = Random()
    fun fetchQuotesFromDb(): Observable<List<Quote>> {
        return databaseHelper.fetchQuotesFromDb()
    }

    fun fetchQuotesFromApi(limit: Int): Observable<List<Quote>> {
        val listOfSingleQuotes: List<Single<Quote>> = servicesHelper.getListOfQuotes(limit)

        val observable: Observable<List<Quote>> = Observable.create<List<Quote>> {
            val allQuotes = listOfSingleQuotes
                    .map {
                        it
                                .onErrorReturn {
                                    Quote("", "")
                                }
                                .blockingGet()
                    }
                    .toMutableList()
                    .filter { !it.author.isEmpty() || !it.text.isEmpty() }

            if (allQuotes.isEmpty()) {
                it.onError(Throwable())
            }

            //Assign a random tag for each image
            //PS: This is hardcoded to Unsplash.it's current number of images (1050)
//            for (quote in allQuotes) {
//                quote.imageTag = random.nextInt(1050).toString()
//            }

            it.onNext(allQuotes)
            it.onComplete()
        }

        return observable
    }

    fun setQuotesToDb(quotes: Collection<Quote>): Observable<Quote> {
        return databaseHelper.setQuotesToDb(quotes)
    }
}