package com.obaied.dingerquotes.data

import com.obaied.dingerquotes.data.model.Quote
import com.obaied.dingerquotes.data.remote.QuoteService
import com.obaied.dingerquotes.util.d
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by ab on 02/04/2017.
 */

@Singleton
class DataManager
@Inject constructor(quoteService: QuoteService) {
    private val mQuoteService: QuoteService = quoteService

    fun getQuote(): Single<Quote> {
        d { "getQuote(): " }

        return mQuoteService.getQuote()
    }
}