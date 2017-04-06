package com.obaied.dingerquotes.data.model

import com.google.gson.annotations.SerializedName

/**
 * Created by ab on 02/04/2017.
 */

data class Quote(@SerializedName("quoteAuthor") val author: String,
                 @SerializedName("quoteText") val text: String)
