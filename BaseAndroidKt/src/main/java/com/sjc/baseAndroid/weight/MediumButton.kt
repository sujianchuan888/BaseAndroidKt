package com.sjc.baseAndroid.weight

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.widget.AppCompatButton
import androidx.databinding.BindingAdapter

class MediumButton(context: Context, attrs: AttributeSet?) : AppCompatButton(context, attrs) {
    /**
     * 初始化字体
     * @param context
     */
    private fun init(context: Context) {
        //设置字体样式
        setTypeface(MediumManager.setFont(context, 4))
    }


    init {
        init(context)
    }
}