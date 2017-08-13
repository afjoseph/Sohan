package com.obaied.dingerquotes.data

import com.obaied.dingerquotes.data.local.DatabaseHelper
import com.obaied.dingerquotes.data.model.Quote
import com.obaied.dingerquotes.data.model.RandomImage
import com.obaied.dingerquotes.data.remote.QuoteService
import com.obaied.dingerquotes.data.remote.RandomImageService
import com.obaied.dingerquotes.data.remote.ServiceHelper
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
                    val randomImageService: RandomImageService,
                    val databaseHelper: DatabaseHelper,
                    val serviceHelper: ServiceHelper) {
    fun fetchQuotesFromDb(limit: Int): Observable<List<Quote>> {
        return databaseHelper.fetchQuotesFromDb(limit)
    }

    fun fetchQuotesFromApi(limit: Int): Observable<Quote> {
        val listOfSingleQuotes = serviceHelper.getListOfQuotes(limit)

        val observable: Observable<Quote> = Observable.create<List<Quote>> {
            val allQuotes = listOfSingleQuotes
                    .map {
                        it
                                .onErrorReturnItem(Quote("", ""))
                                .blockingGet()
                    }
                    .toMutableList()
                    .filter { !it.author.isEmpty() && !it.text.isEmpty() }

            it.onNext(allQuotes)
            it.onComplete()
        }.concatMap { databaseHelper.setQuotesToDb(it) }

        return observable
    }

    fun fetchRandomImage(): Single<RandomImage> {
        return randomImageService.getRandomImage()
    }
}