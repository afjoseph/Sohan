package com.joseph.sohan.data.local

import android.content.ContentValues
import android.database.Cursor
import com.joseph.sohan.data.model.Quote

/**
 * Created by ab on 06.08.17.
 */

object Db {
    object DbQuotesTable {
        val TABLE_NAME = "db_quotes"

        val COLUMN_AUTHOR = "author"
        val COLUMN_TEXT = "text"
        val COLUMN_IMAGE_TAG = "image_tag"

        val CREATE = "CREATE TABLE " + TABLE_NAME + " ( " +
                COLUMN_AUTHOR + " TEXT NOT NULL, " +
                COLUMN_TEXT + " TEXT NOT NULL, " +
                COLUMN_IMAGE_TAG + " TEXT NULL" +
                " ); "

        fun toContentValues(quotes: Quote): ContentValues {
            val values = ContentValues()
            values.put(COLUMN_AUTHOR, quotes.author)
            values.put(COLUMN_TEXT, quotes.text)
            values.put(COLUMN_IMAGE_TAG, quotes.imageTag)
            return values
        }

        fun parseCursor(cursor: Cursor): Quote {
            val author = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_AUTHOR))
            val text = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TEXT))
            val imageTag = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_IMAGE_TAG))

            return Quote(
                    author,
                    text,
                    imageTag
            )
        }
    }
}