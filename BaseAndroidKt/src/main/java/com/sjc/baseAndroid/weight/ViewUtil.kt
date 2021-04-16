package com.sjc.baseAndroid.weight

import android.view.View
import android.view.ViewTreeObserver
//获取控件宽高
object ViewUtil {

    fun getViewWidth(view: View, onViewListener: OnViewListener?) {
        val vto2 = view.viewTreeObserver
        vto2.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                view.viewTreeObserver.removeOnGlobalLayoutListener(this)
                onViewListener?.onView(view.width, view.height)
            }
        })
    }

    interface OnViewListener {
        fun onView(width: Int, height: Int)
    }
}