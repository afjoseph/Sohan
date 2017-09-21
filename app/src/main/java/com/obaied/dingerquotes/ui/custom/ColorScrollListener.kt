package com.obaied.dingerquotes.ui.custom

/**
 * Created by ab on 09.08.17.
 */

import android.animation.ArgbEvaluator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView

import com.obaied.colours.Colour

/**
 * InfiniteScrollListener, which can be added to RecyclerView with addOnScrollListener
 * to detect moment when RecyclerView was scrolled to the end.
 */
abstract class ColorScrollListener(secondcolor: Int, private val callback: ColorDyCallback) : RecyclerView.OnScrollListener() {
    private val dt: Int = 0

    internal fun lerp(a: Float, b: Float, f: Float): Float {
        return a + f * (b - a)
    }

    override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        if (dy < 5) {
            return
        }

    }

    internal interface ColorDyCallback {
        fun onChangeColor(newColor: Int)
    }

}
