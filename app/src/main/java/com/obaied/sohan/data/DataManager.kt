package com.obaied.sohan.data

import com.obaied.sohan.data.local.DatabaseHelper
import com.obaied.sohan.data.model.Quote
import com.obaied.sohan.data.remote.QuoteService
import com.obaied.sohan.data.remote.ServicesHelper
import io.reactivex.Observable
import io.reactivex.Single
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