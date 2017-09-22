package com.obaied.sohan.util

import com.obaied.sohan.data.model.Quote
import com.obaied.sohan.data.model.RandomImage
import java.util.*

/**
 * Created by ab on 08/04/2017.
 */

object DummyDataFactory {
    fun makeQuote(): Quote {
        return Quote(randomUuid(), randomUuid())
    }

    fun randomUuid(): String {
        return UUID.randomUUID().toString()
    }

    fun makeQuotes(limit: Int): List<Quote> {
        val quotesList = mutableListOf<Quote>()
        for (i in 0 until limit) {
            quotesList.add(makeQuote())
        }

        return quotesList
    }

    fun makeRandomImage(): RandomImage {
        return RandomImage(randomUuid(), randomUuid())
    }
}
