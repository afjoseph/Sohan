package com.obaied.dingerquotes.data.local

import android.content.ContentValues
import android.database.Cursor
import com.obaied.dingerquotes.data.model.Quote
import com.obaied.dingerquotes.util.d

/**
 * Created by ab on 06.08.17.
 */

object Db {
    object DbQuotesTable {
        val TABLE_NAME = "db_quotes"

        val COLUMN_ID = "id"
        val COLUMN_AUTHOR = "author"
        val COLUMN_TEXT = "text"

        val CREATE = "CREATE TABLE " + TABLE_NAME + " ( " +
                COLUMN_AUTHOR + " TEXT NOT NULL, " +
                COLUMN_TEXT + " TEXT NOT NULL" +
                " ); "

        fun toContentValues(quotes: Quote): ContentValues {
            val values = ContentValues()
            values.put(COLUMN_AUTHOR, quotes.author)
            values.put(COLUMN_TEXT, quotes.text)
            return values
        }

        fun parseCursor(cursor: Cursor): Quote {
            val author = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_AUTHOR))
            val text = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TEXT))
//            d { "parseCursor(): {$author} | {$text}" }

            return Quote(
                    author,
                    text
            )
        }
    }
}