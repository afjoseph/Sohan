package com.joseph.sohan

import com.joseph.sohan.data.local.DatabaseHelper
import com.joseph.sohan.data.local.Db
import com.joseph.sohan.data.local.DbOpenHelper
import com.joseph.sohan.data.model.Quote
import com.joseph.sohan.util.DummyDataFactory
import com.joseph.sohan.util.Schedulers.TestSchedulerProvider
import io.reactivex.observers.TestObserver
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config
import kotlin.test.assertEquals

/**
 * Created by ab on 13.08.17.
 */
@RunWith(RobolectricTestRunner::class)
@Config(constants = BuildConfig::class, sdk = intArrayOf(24))
class DatabaseHelperTest {
    val dbOpenHelper = DatabaseHelper(DbOpenHelper(RuntimeEnvironment.application),
            TestSchedulerProvider())

    @Test
    fun verifyTestsWork() {
        assert(true)
    }

    @Test
    fun setQuotesToDb() {
        val quotes = DummyDataFactory.makeQuotes(2)

        val testObserver = TestObserver<Quote>()
        dbOpenHelper.setQuotesToDb(quotes).subscribe(testObserver)
        testObserver.assertNoErrors()
        testObserver.assertValueCount(2)

        val cursor = dbOpenHelper.db.query("SELECT * FROM ${Db.DbQuotesTable.TABLE_NAME}")
        assertEquals(cursor.count, 2)

        for (quote in quotes) {
            cursor.moveToNext()
            assertEquals(quote, Db.DbQuotesTable.parseCursor(cursor))
        }
    }

    @Test
    fun fetchQuotesFromDb() {
        val quotes = DummyDataFactory.makeQuotes(2)

        dbOpenHelper.setQuotesToDb(quotes).subscribe()

        val testObserver = TestObserver<List<Quote>>()
        dbOpenHelper.fetchQuotesFromDb().subscribe(testObserver)

        testObserver.assertNoErrors()
        testObserver.assertValue(quotes)
    }
}
