package com.sjc.baseAndroid.weight

import android.content.Context
import android.graphics.Typeface

object BoldManager {
    // fongUrl是自定义字体分类的名称
    private const val typeface_bold = "fonts/bold.TTF"

    //Typeface是字体，这里我们创建一个对象
    private var tf: Typeface? = null

    /**
     * 设置字体
     */
    fun setFont(context: Context, type: Int): Typeface? {
        if (tf == null) {
            //给它设置你传入的自定义字体文件，再返回回来
            tf = Typeface.createFromAsset(context.assets, typeface_bold)
        }
        return tf
    }
}