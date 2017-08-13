package com.obaied.dingerquotes

import com.nhaarman.mockito_kotlin.*
import com.obaied.dingerquotes.data.DataManager
import com.obaied.dingerquotes.data.model.Quote
import com.obaied.dingerquotes.ui.start.StartMvpView
import com.obaied.dingerquotes.ui.start.StartPresenter
import com.obaied.dingerquotes.util.DummyDataFactory
import com.obaied.dingerquotes.util.Schedulers.TestSchedulerProvider
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.util.*

/**
 * Created by ab on 13.08.17.
 */

class StartPresenterTest {
    lateinit var mockStartMvpView: StartMvpView
    lateinit var mockDataManager: DataManager
    private lateinit var startPresenter: StartPresenter

    @Before
    fun setup() {
        mockStartMvpView = mock<StartMvpView>()
        mockDataManager = mock<DataManager>()
        startPresenter = StartPresenter(
                mockDataManager,
                CompositeDisposable(),
                TestSchedulerProvider()
        )

        startPresenter.attachView(mockStartMvpView)
    }

    @After
    fun teardown() {
        startPresenter.detachView()
    }

    @Test
    fun verifyTestsWork() {
        assert(true)
    }

    @Test
    fun fetchQuotes_normal() {
        val quotes = DummyDataFactory.makeQuotes(10)

        whenever(mockDataManager.fetchQuotesFromDb(any<Int>()))
                .thenReturn(Observable.just(quotes))

        startPresenter.subscribeToDbToFetchQuotes(0)
        verify(mockStartMvpView).showQuotes(quotes)
        verify(mockStartMvpView, never()).showEmpty()
        verify(mockStartMvpView, never()).showError(any<String>())
    }

    @Test
    fun fetchQuotes_empty() {
        whenever(mockDataManager.fetchQuotesFromDb(any<Int>()))
                .thenReturn(Observable.just(Collections.emptyList()))

        startPresenter.subscribeToDbToFetchQuotes(0)
        verify(mockStartMvpView).showEmpty()
        verify(mockStartMvpView, never()).showQuotes(any<List<Quote>>())
        verify(mockStartMvpView, never()).showError(any<String>())
    }

    @Test
    fun fetchQuotes_fail() {
        whenever(mockDataManager.fetchQuotesFromDb(any<Int>()))
                .thenReturn(Observable.error(RuntimeException()))

        startPresenter.subscribeToDbToFetchQuotes(0)
        verify(mockStartMvpView).showError(any<String>())
        verify(mockStartMvpView, never()).showQuotes(any<List<Quote>>())
        verify(mockStartMvpView, never()).showEmpty()
    }
}
