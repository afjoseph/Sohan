package com.joseph.sohan.util

import android.content.res.Resources


/**
 * Created by ab on 12.04.17.
 */

class DisplayMetricsUtil {
    companion object {
        fun isScreenW(widthDp: Int): Boolean {
            val displayMetrics = Resources.getSystem().displayMetrics
            val screenWidth = displayMetrics.widthPixels / displayMetrics.density
            return screenWidth >= widthDp
        }

        fun getScreenMetrics(): Pair<Int, Int> {
            val displayMetrics = Resources.getSystem().displayMetrics
            val screenWidth = displayMetrics.widthPixels
            val screenHeight = displayMetrics.heightPixels

            return Pair(screenWidth, screenHeight)
        }
    }
}
