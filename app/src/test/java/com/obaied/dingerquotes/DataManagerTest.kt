package com.obaied.dingerquotes

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import com.obaied.dingerquotes.data.DataManager
import com.obaied.dingerquotes.data.local.DatabaseHelper
import com.obaied.dingerquotes.data.model.Quote
import com.obaied.dingerquotes.data.model.RandomImage
import com.obaied.dingerquotes.data.remote.QuoteService
import com.obaied.dingerquotes.data.remote.RandomImageService
import com.obaied.dingerquotes.data.remote.ServiceHelper
import com.obaied.dingerquotes.util.DummyDataFactory
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.observers.TestObserver
import org.junit.Before
import org.junit.Test

/**
 * Created by ab on 13.08.17.
 */
class DataManagerTest {
    lateinit var mockDatabaseHelper: DatabaseHelper
    lateinit var mockQuoteService: QuoteService
    lateinit var mockRandomImageService: RandomImageService
    lateinit var mockServiceHelper: ServiceHelper
    private lateinit var dataManager: DataManager

    @Before
    fun setup() {
        mockDatabaseHelper = mock<DatabaseHelper>()
        mockQuoteService = mock<QuoteService>()
        mockRandomImageService = mock<RandomImageService>()
        mockServiceHelper = mock<ServiceHelper>()
        dataManager = DataManager(mockQuoteService, mockRandomImageService, mockDatabaseHelper, mockServiceHelper)
    }

    @Test
    fun verifyTestsWork() {
        assert(true)
    }

    @Test
    fun fetchQuotesFromApi() {
        val quotes = DummyDataFactory.makeQuotes(2)
        val listOfSingleQuotes = listOf<Single<Quote>>(
                Single.just(quotes[0]),
                Single.just(quotes[1])
        )

        whenever(mockDatabaseHelper.setQuotesToDb(quotes))
                .thenReturn(Observable.fromIterable(quotes))

        whenever(mockServiceHelper.getListOfQuotes(any<Int>()))
                .thenReturn(listOfSingleQuotes)

        val testObserver = TestObserver<Quote>()
        dataManager.fetchQuotesFromApi(any<Int>()).subscribe(testObserver)
        testObserver.assertNoErrors()
        testObserver.assertValues(quotes[0], quotes[1])
        testObserver.assertValueCount(2)
    }

    @Test
    fun fetchQuotesFromDb() {
        val quotes = DummyDataFactory.makeQuotes(2)

        whenever(mockDatabaseHelper.fetchQuotesFromDb(any<Int>()))
                .thenReturn(Observable.just(quotes))

        val testObserver = TestObserver<List<Quote>>()
        dataManager.fetchQuotesFromDb(0).subscribe(testObserver)

        testObserver.assertNoErrors()
        testObserver.assertValue(quotes)
    }

    @Test
    fun fetchRandomImage() {
        val randomImage = DummyDataFactory.makeRandomImage()

        whenever(mockRandomImageService.getRandomImage())
                .thenReturn(Single.just(randomImage))

        val testObserver = TestObserver<RandomImage>()
        dataManager.fetchRandomImage().subscribe(testObserver)

        testObserver.assertNoErrors()
        testObserver.assertValue(randomImage)
    }

    //TODO: Test When Quote API fails
    //TODO: Test When RandomImage API fails
}