package com.obaied.sohan.data.local

import com.obaied.sohan.data.model.Quote
import com.obaied.sohan.data.model.RandomImage
import com.obaied.sohan.util.Schedulers.SchedulerProvider
import com.obaied.sohan.util.d
import com.squareup.sqlbrite2.BriteDatabase
import com.squareup.sqlbrite2.SqlBrite
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by ab on 06.08.17.
 */
@Singleton
class DatabaseHelper
@Inject constructor(dbOpenHelper: DbOpenHelper,
                    schedulerProvider: SchedulerProvider) {
    val db: BriteDatabase
            = SqlBrite.Builder().build().wrapDatabaseHelper(dbOpenHelper, schedulerProvider.io())

    fun fetchQuotesFromDb(): Observable<List<Quote>> {
        return db.createQuery(Db.DbQuotesTable.TABLE_NAME,
                "SELECT * FROM " + Db.DbQuotesTable.TABLE_NAME)
                .mapToList { Db.DbQuotesTable.parseCursor(it) }
    }

    fun setQuotesToDb(newQuotes: Collection<Quote>): Observable<Quote> {
        d { "setQuotesToDb(): " }
        return Observable.create<Quote> {
            val transaction = db.newTransaction()
            try {
                for (quote in newQuotes) {
                    db.insert(Db.DbQuotesTable.TABLE_NAME,
                            Db.DbQuotesTable.toContentValues(quote))

                    it.onNext(quote)
                }

                transaction.markSuccessful()
                it.onComplete()
            } finally {
                transaction.end()
            }
        }
    }

    fun updateQuotesinDb(newImages: List<RandomImage>): Observable<Quote> {
        d { "updateQuotesinDb(): " }
        d { "updateQuotesinDb(): size of newImage ${newImages.size}" }

        return db.createQuery(Db.DbQuotesTable.TABLE_NAME,
                "SELECT * FROM ${Db.DbQuotesTable.TABLE_NAME} LIMIT ${newImages.size}")

                .mapToList { Db.DbQuotesTable.parseCursor(it) }

                .concatMap { quotes: MutableList<Quote> ->
                    d { "updateQuotesinDb(): map(): num of quotes fetched from db ${quotes.size}" }
                    Observable.create<Quote> {
                        val transaction = db.newTransaction()
                        try {
                            for (i in 0 until quotes.size) {
                                val quote = quotes[i]

                                db.update(Db.DbQuotesTable.TABLE_NAME,
                                        Db.DbQuotesTable.toContentValues(quote),
                                        "image_url='${newImages[i].url}'")

                                it.onNext(quote)
                            }

                            transaction.markSuccessful()
                            it.onComplete()
                        } finally {
                            transaction.end()
                        }
                    }
                }
    }
}
