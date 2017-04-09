package com.obaied.dingerquotes.ui.quote

import com.obaied.dingerquotes.data.DataManager
import com.obaied.dingerquotes.data.model.Quote
import com.obaied.dingerquotes.util.TestDataFactory
import com.obaied.dingerquotes.util.TestSchedulerProvider
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.runners.MockitoJUnitRunner

/**
 * Created by ab on 09/04/2017.
 */

@RunWith(MockitoJUnitRunner::class)
class QuotePresenterTest {
    @Mock internal var mMockDataManager: DataManager? = null
    @Mock internal var mMockQuoteMvpView: QuoteMvpView? = null

    internal var mQuotePresenter: QuotePresenter? = null

    @Before
    fun setUp() {
        mQuotePresenter = QuotePresenter(mMockDataManager!!,
                CompositeDisposable(),
                TestSchedulerProvider())
        mQuotePresenter?.attachView(mMockQuoteMvpView!!)
    }

    @Test
    fun getQuoteSuccessful() {
        val quote = TestDataFactory.statics.makeQuote()
        stubDataManagerGetQuote(Single.just(quote))

        mQuotePresenter?.getQuote()

        Mockito.verify(mMockQuoteMvpView)!!.showQuote(quote)
    }

    @Test
    fun getQuoteFailure() {
        stubDataManagerGetQuote(Single.error(Throwable()))

        mQuotePresenter?.getQuote()

        Mockito.verify(mMockQuoteMvpView)!!.showError()
    }

    @Test
    fun getQuoteEmpty() {
        //TODO: INCOMPLETE
    }

    @Test
    fun getQuoteNoInternet() {
        //TODO: INCOMPLETE
    }

    private fun stubDataManagerGetQuote(single: Single<Quote>) {
        Mockito.`when`(mMockDataManager?.getQuote()).thenReturn(single)
    }

    @After
    fun detachView() {
        mQuotePresenter?.detachView()
    }
}