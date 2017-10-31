package com.joseph.sohan

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import com.joseph.sohan.data.DataManager
import com.joseph.sohan.data.local.DatabaseHelper
import com.joseph.sohan.data.model.Quote
import com.joseph.sohan.data.remote.QuoteService
import com.joseph.sohan.data.remote.ServicesHelper
import com.joseph.sohan.util.DummyDataFactory
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.observers.TestObserver
import org.junit.Before
import org.junit.Test

/**
 * Created by ab on 13.08.17.
 */
//class DataManagerTest {
//    lateinit var mockDatabaseHelper: DatabaseHelper
//    lateinit var mockQuoteService: QuoteService
//    lateinit var mockServicesHelper: ServicesHelper
//    private lateinit var dataManager: DataManager
//
//    @Before
//    fun setup() {
//        mockDatabaseHelper = mock<DatabaseHelper>()
//        mockQuoteService = mock<QuoteService>()
//        mockServicesHelper = mock<ServicesHelper>()
//        dataManager = DataManager(mockQuoteService, mockDatabaseHelper, mockServicesHelper)
//    }
//
//    @Test
//    fun verifyTestsWork() {
//        assert(true)
//    }
//
//    @Test
//    fun fetchQuotesFromApi() {
//        //Prepare
//        //====================
//        val quotes = DummyDataFactory.makeQuotes(2)
//        val listOfSingleQuotes = listOf<Single<Quote>>(
//                Single.just(quotes[0]),
//                Single.just(quotes[1])
//        )
//
//        whenever(mockDatabaseHelper.setQuotesToDb(quotes))
//                .thenReturn(Observable.fromIterable(quotes))
//
//        whenever(mockServicesHelper.getListOfQuotes(any<Int>()))
//                .thenReturn(listOfSingleQuotes)
//
//        //Execute
//        //====================
//        val testObserver = TestObserver<Quote>()
//        dataManager.fetchQuotesFromApi(any<Int>()).subscribe(testObserver)
//
//        //Assert
//        //====================
//        testObserver.assertNoErrors()
//        testObserver.assertValues(quotes[0], quotes[1])
//        testObserver.assertValueCount(2)
//    }
//
//    @Test
//    fun fetchQuotesFromDb() {
//        val quotes = DummyDataFactory.makeQuotes(2)
//
//        whenever(mockDatabaseHelper.fetchQuotesFromDb())
//                .thenReturn(Observable.just(quotes))
//
//        val testObserver = TestObserver<List<Quote>>()
//        dataManager.fetchQuotesFromDb().subscribe(testObserver)
//
//        testObserver.assertNoErrors()
//        testObserver.assertValue(quotes)
//    }
//
//    @Test
//    fun fetchRandomImage() {
//        val randomImage = DummyDataFactory.makeRandomImage()
//
//        whenever(mockRandomImageService.getRandomImage())
//                .thenReturn(Single.just(randomImage))
//
//        val testObserver = TestObserver<RandomImage>()
//        dataManager.fetchRandomImage().subscribe(testObserver)
//
//        testObserver.assertNoErrors()
//        testObserver.assertValue(randomImage)
//    }

    //TODO: Test When Quote API fails
    //TODO: Test When RandomImage API fails
//}