package com.obaied.dingerquotes

import android.support.v7.widget.RecyclerView
import android.widget.TextView
import com.obaied.dingerquotes.data.local.DatabaseHelper
import com.obaied.dingerquotes.data.local.Db
import com.obaied.dingerquotes.data.local.DbOpenHelper
import com.obaied.dingerquotes.data.model.Quote
import com.obaied.dingerquotes.ui.start.StartActivity
import com.obaied.dingerquotes.util.DummyDataFactory
import com.obaied.dingerquotes.util.Schedulers.TestSchedulerProvider
import io.reactivex.internal.util.HalfSerializer
import io.reactivex.observers.TestObserver
import io.reactivex.subscribers.TestSubscriber
import kotlinx.android.synthetic.main.activity_start.*
import org.hamcrest.Matchers
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
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
    fun basicTest() {
//        val activity = Robolectric.setupActivity(StartActivity::class.java)
//        val textView = activity.start_recyclerview as RecyclerView
//
//        Assert.assertEquals(1, 1)
//        Assert.assertNotNull(textView)
//        assertThat(textView.text).isEqualTo("Hello World!")
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
        dbOpenHelper.fetchQuotesFromDb(0).subscribe(testObserver)

        testObserver.assertNoErrors()
        testObserver.assertValue(quotes)
    }
}
