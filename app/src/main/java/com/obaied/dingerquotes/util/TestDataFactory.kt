package com.obaied.dingerquotes.util

import android.media.Image
import com.obaied.dingerquotes.data.model.Quote
import java.util.*

/**
 * Created by ab on 08/04/2017.
 */

class TestDataFactory {
    object statics {
        fun makeQuote(): Quote {
            return Quote(randomUuid(), randomUuid())
        }

        fun randomUuid(): String {
            return UUID.randomUUID().toString()
        }
    }

}
