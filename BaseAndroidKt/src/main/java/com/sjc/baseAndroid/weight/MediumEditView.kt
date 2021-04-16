package com.sjc.baseAndroid.weight

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText

class MediumEditView(context: Context, attrs: AttributeSet?) : AppCompatEditText(context, attrs) {
    /**
     * 初始化字体
     * @param context
     */
    private fun init(context: Context) {
        //设置字体样式
        setTypeface(com.sjc.baseAndroid.weight.MediumManager.setFont(context, 4))
    }

    init {
        init(context)
    }
}