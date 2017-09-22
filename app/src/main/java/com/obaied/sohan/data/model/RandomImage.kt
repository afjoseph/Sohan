package com.obaied.sohan.data.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

/**
 * Created by ab on 10.08.17.
 * "id": 6477,
"url": "https://splashbase.s3.amazonaws.com/moveast/regular/tumblr_o5xz5833UG1tomxvuo1_1280.jpg",
"large_url": "https://splashbase.s3.amazonaws.com/moveast/large/tumblr_o5xz5833UG1tomxvuo1_1280.jpg",
"source_id": null,
"copyright": "CC0",
"site": "moveast"
 */

data class RandomImage(
        @SerializedName("id") val id: String,
        @SerializedName("url") val url: String) : Parcelable {
    companion object {
        @JvmField val CREATOR: Parcelable.Creator<RandomImage> = object : Parcelable.Creator<RandomImage> {
            override fun createFromParcel(source: Parcel): RandomImage = RandomImage(source)
            override fun newArray(size: Int): Array<RandomImage?> = arrayOfNulls(size)
        }
    }

    constructor(source: Parcel) : this(
            source.readString(),
            source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(id)
        dest.writeString(url)
    }
}
