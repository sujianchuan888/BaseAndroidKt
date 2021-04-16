package com.sjc.baseAndroid.weight

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import com.sjc.baseAndroid.weight.LightManager.setFont

class LightTextView(context: Context, attrs: AttributeSet?) : AppCompatTextView(context, attrs) {
    /**
     * 初始化字体
     * @param context
     */
    private fun init(context: Context) {
        //设置字体样式
        typeface = setFont(context, 2)
    }

    init {
        init(context)
    }
}