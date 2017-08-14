package com.obaied.dingerquotes.data.remote

import com.obaied.dingerquotes.data.model.Quote
import io.reactivex.Single
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by ab on 13.08.17.
 */
@Singleton
class ServicesHelper
@Inject constructor(val quoteService: QuoteService,
                    val randomImageService: RandomImageService) {
    private val random = Random()
    private val RANDOM_SEED_MAX = Int.MAX_VALUE

    fun getListOfQuotes(limit: Int): List<Single<Quote>> {
        val obsList = mutableListOf<Single<Quote>>()
        for (i in 0..limit) {
            obsList.add(quoteService.getQuote(random.nextInt(RANDOM_SEED_MAX).toString()))
        }

        return obsList
    }

}
