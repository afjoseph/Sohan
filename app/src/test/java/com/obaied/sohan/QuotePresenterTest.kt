package com.joseph.sohan

import com.nhaarman.mockito_kotlin.*
import com.joseph.sohan.data.DataManager
import com.joseph.sohan.ui.quote.QuoteMvpView
import com.joseph.sohan.ui.quote.QuotePresenter
import com.joseph.sohan.util.Schedulers.TestSchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import org.junit.After
import org.junit.Before
import org.junit.Test

/**
 * Created by ab on 13.08.17.
 */

class QuotePresenterTest {
    lateinit var mockQuoteMvpView: QuoteMvpView
    lateinit var mockDataManager: DataManager
    private lateinit var quotePresenter: QuotePresenter

    @Before
    fun setup() {
        mockQuoteMvpView = mock<QuoteMvpView>()
        mockDataManager = mock<DataManager>()
        quotePresenter = QuotePresenter(
                mockDataManager,
                CompositeDisposable(),
                TestSchedulerProvider()
        )

        quotePresenter.attachView(mockQuoteMvpView)
    }

    @After
    fun teardown() {
        quotePresenter.detachView()
    }

    @Test
    fun verifyTestsWork() {
        assert(true)
    }
//
//    @Test
//    fun fetchRandomImage_normal() {
//        val randomImage = DummyDataFactory.makeRandomImage()
//
//        whenever(mockDataManager.fetchRandomImage())
//                .thenReturn(Single.just(randomImage))
//
//        quotePresenter.fetchRandomImage()
//        verify(mockQuoteMvpView).showImage(randomImage.url)
//        verify(mockQuoteMvpView, never()).showError(any<String>())
//    }
//
//    @Test
//    fun fetchRandomImage_fail() {
//        whenever(mockDataManager.fetchRandomImage())
//                .thenReturn(Single.error(RuntimeException()))
//
//        quotePresenter.fetchRandomImage()
//        verify(mockQuoteMvpView).showError(any<String>())
//        verify(mockQuoteMvpView, never()).showImage(any())
//    }
}
