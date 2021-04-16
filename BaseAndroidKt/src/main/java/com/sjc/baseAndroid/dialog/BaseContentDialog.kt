package com.sjc.baseAndroid.dialog

import android.view.*
import com.sjc.baseAndroid.R
import com.sjc.baseAndroid.weight.MediumTextView

/**
 * 题库提示弹窗
 */
class BaseContentDialog(var title:String,content_ :String, click: View.OnClickListener): BaseDialogFragment(click) {
    var content = content_
    override fun setContentView(): Int {
        return R.layout.dialog_base_content
    }

    override fun initView() {
        var text_cancel = mView!!.findViewById<View>(R.id.text_cancel)
        var text_sure = mView!!.findViewById<View>(R.id.text_sure)
        var text_content = mView!!.findViewById<MediumTextView>(R.id.text_content)
        text_content.text = content
        text_cancel.setOnClickListener {
            dismiss()
        }
        text_sure.setOnClickListener {
            mClick.onClick(it)
        }

    }


}