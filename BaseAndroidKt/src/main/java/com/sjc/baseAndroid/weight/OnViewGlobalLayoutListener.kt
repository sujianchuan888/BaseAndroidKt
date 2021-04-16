package com.sjc.baseAndroid.weight

import android.view.View
import android.view.ViewTreeObserver.OnGlobalLayoutListener

class OnViewGlobalLayoutListener(private val view: View, height: Int) : OnGlobalLayoutListener {
    private var maxHeight = 500
    override fun onGlobalLayout() {
        if (view.height > maxHeight) view.layoutParams.height = maxHeight
    }

    init {
        maxHeight = height
    }
}