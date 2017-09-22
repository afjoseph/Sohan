package com.obaied.sohan.ui.custom

/**
 * Created by ab on 09.08.17.
 */

import android.graphics.Color
import android.support.v7.widget.RecyclerView

import com.obaied.colours.Colour

/**
 * InfiniteScrollListener, which can be added to RecyclerView with addOnScrollListener
 * to detect moment when RecyclerView was scrolled to the end.
 */
class ColorScrollListener(private val callback: ColorDyCallback) : RecyclerView.OnScrollListener() {
    private var dt: Int = 0
    private var didEnterAtLeastOnce = false
    private val startColorArr = intArrayOf(Colour.fadedBlueColor(), Colour.hollyGreenColor())
    private val centerColorArr = intArrayOf(Colour.hollyGreenColor(), Colour.cactusGreenColor())
    private val endColorArr = intArrayOf(Colour.mandarinColor(), Colour.peachColor())

    override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        if (didEnterAtLeastOnce && (dy < 1 && dy > -1)) {
            return
        }

        didEnterAtLeastOnce = true

        dt += dy / 15
        val oscilatingDt = Math.sin(Math.toRadians(dt.toDouble()))

        //evaluate colors

        val newStartColor = interpolateColor(oscilatingDt.toFloat(), startColorArr[0], startColorArr[1])
        val newCenterColor = interpolateColor(oscilatingDt.toFloat(), centerColorArr[0], centerColorArr[1])
        val newEndColor = interpolateColor(oscilatingDt.toFloat(), endColorArr[0], endColorArr[1])

        callback.onChangeColor(
                newStartColor,
                newCenterColor,
                newEndColor
        )
    }

    private fun interpolate(a: Float, b: Float, proportion: Float): Float {
        return a + (b - a) * proportion
    }

    /** Returns an interpoloated color, between `a` and `b`  */
    private fun interpolateColor(proportion: Float, a: Int, b: Int): Int {
        val hsva = FloatArray(3)
        val hsvb = FloatArray(3)
        Color.colorToHSV(a, hsva)
        Color.colorToHSV(b, hsvb)
        for (i in 0..2) {
            hsvb[i] = interpolate(hsva[i], hsvb[i], proportion)
        }
        return Color.HSVToColor(hsvb)
    }

    interface ColorDyCallback {
        fun onChangeColor(newStartColor: Int, newCenterColor: Int, newEndColor: Int)
    }

}
