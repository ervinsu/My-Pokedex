package com.ervin.library_common.util

import android.content.Context
import android.util.DisplayMetrics

fun calculateNoOfColumn(context: Context, widthColumnInFloat: Float): Int {
    val display: DisplayMetrics = context.resources.displayMetrics
    val screen = display.widthPixels / display.density
    return (screen / widthColumnInFloat + 0.5).toInt()
}