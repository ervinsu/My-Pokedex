package com.ervin.library_common.util

import android.app.Activity
import android.app.ActivityManager
import android.content.Context
import android.util.DisplayMetrics
import android.view.View
import android.view.inputmethod.InputMethodManager

fun calculateNoOfColumn(context: Context, widthColumnInFloat: Float): Int {
    val display: DisplayMetrics = context.resources.displayMetrics
    val screen = display.widthPixels / display.density
    return (screen / widthColumnInFloat + 0.5).toInt()
}

fun Activity?.hideKeyboard() {
    this ?: return
    val imm = this.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    val view = this.currentFocus ?: View(this)
    imm.hideSoftInputFromWindow(view.windowToken, 0)
}

fun Context.isServiceRunning(serviceClass: Class<*>): Boolean {
    val manager = (this.getSystemService(Context.ACTIVITY_SERVICE)) as ActivityManager
    for (service in manager.getRunningServices(Int.MAX_VALUE)) {
        if (serviceClass.name == service.service.className) {
            return true
        }
    }
    return false
}