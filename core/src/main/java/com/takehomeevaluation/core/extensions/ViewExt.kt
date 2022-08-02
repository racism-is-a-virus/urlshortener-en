package com.takehomeevaluation.core.extensions

import android.app.Activity
import android.view.View
import android.view.inputmethod.InputMethodManager

fun View.closeKeyBoard() {
    (this.context.getSystemService(Activity.INPUT_METHOD_SERVICE) as? InputMethodManager?)
        ?.hideSoftInputFromWindow(windowToken, 0)
}