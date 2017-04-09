package com.obaied.dingerquotes.data

import com.obaied.dingerquotes.data.model.Quote
import com.obaied.dingerquotes.data.remote.QuoteService
import com.obaied.dingerquotes.util.TestDataFactory
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.runners.MockitoJUnitRunner


/**
 * Created by ab on 08/04/2017.
 */

@RunWith(MockitoJUnitRunner::class)
class DataManagerTest {
    @Mock internal var mMockQuoteService: QuoteService? = null
    internal var mDataManager: DataManager? = null

    @Before
    fun setup() {
        mDataManager = DataManager(mMockQuoteService!!)
    }

    @Test
    fun getQuoteComplete() {
        val quote = TestDataFactory.statics.makeQuote()
        stubQuoteServiceGetQuote(Single.just(quote))

        mDataManager?.getQuote()!!
                .test()
                .assertComplete()
                .assertValue { it == quote }
    }

    private fun stubQuoteServiceGetQuote(single: Single<Quote>) {
        Mockito.`when`(mMockQuoteService?.getQuote()).thenReturn(single)
    }
}