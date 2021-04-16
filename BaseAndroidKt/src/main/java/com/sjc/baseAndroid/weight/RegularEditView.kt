package com.sjc.baseAndroid.weight

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText

class RegularEditView(context: Context, attrs: AttributeSet?) : AppCompatEditText(context, attrs) {
    /**
     * 初始化字体
     * @param context
     */
    private fun init(context: Context) {
        //设置字体样式
        typeface = RegularManager.setFont(context, 3)
    }

    init {
        init(context)
    }
}