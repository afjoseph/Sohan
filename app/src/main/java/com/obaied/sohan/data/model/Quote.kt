package com.obaied.sohan.data.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

/**
 * Created by ab on 02/04/2017.
 */

data class Quote(
        @SerializedName("quoteAuthor") val author: String,
        @SerializedName("quoteText") val text: String,
        var imageTag: String? = null, //Don't sync with DB or parcel it, just keep it saved in memory
        var colorFilter: Int? = null //Don't sync with DB or parcel it, just keep it saved in memory
) : Parcelable {


    companion object {
        @JvmField val CREATOR: Parcelable.Creator<Quote> = object : Parcelable.Creator<Quote> {
            override fun createFromParcel(source: Parcel): Quote = Quote(source)
            override fun newArray(size: Int): Array<Quote?> = arrayOfNulls(size)
        }
    }

    constructor(source: Parcel) : this(source.readString(), source.readString())

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeString(author)
        dest?.writeString(text)
    }
}
