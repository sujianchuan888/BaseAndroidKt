package com.sjc.baseAndroid.dialog

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.text.TextUtils
import android.view.Gravity
import android.view.KeyEvent
import android.view.View
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.TextView
import com.sjc.baseAndroid.R
import com.sjc.baseAndroid.utils.CustomToast

class BaseLoadingDialog(ctx: Context?, isTouchOut: Boolean, isCancel: Boolean, tipMsg: String?) :
    Dialog(
        ctx!!, R.style.LoadingDialog
    ) {
    /**
     * if the mDialogTextView don't dimiss, what is the tips.
     */
    private var tipMsg = ""
    private var mShowMessage: TextView? = null

    /**
     * 点击系统返回按钮是否消失
     */
    private val isCancel: Boolean
    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (tipMsg.isEmpty()) {
            tipMsg = "加载中..."
        }
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (!isCancel) {
                CustomToast.showToast(context, Gravity.CENTER, 0, tipMsg)
                return true
            }
        }
        return super.onKeyDown(keyCode, event)
    }

    companion object {
        /**
         * LoadDialog
         */
        private var loadDialog: BaseLoadingDialog? = null

        /**
         * show the mDialogTextView
         *
         * @param context
         */
        fun show(context: Context) {
            showDialog(context, "", true, false)
        }

        /**
         * show the mDialogTextView
         *
         * @param context Context
         * @param message String
         */
        fun show(context: Context, message: String) {
            showDialog(context, message, true, false)
        }

        /**
         * show the mDialogTextView
         *
         * @param context  Context
         * @param isCancel boolean
         */
        fun show(context: Context, isTouchOut: Boolean, isCancel: Boolean) {
            showDialog(context, "", isTouchOut, isCancel)
        }

        /**
         * show the mDialogTextView
         *
         * @param context  Context
         * @param message  String, show the message to user when isCancel is true.
         * @param isCancel boolean, true is can't dimiss，false is can dimiss
         */
        fun show(context: Context, message: String?, isTouchOut: Boolean, isCancel: Boolean) {
            showDialog(context, message, isTouchOut, isCancel)
        }

        /**
         * show the mDialogTextView
         *
         * @param context    Context
         * @param message    String, show the message to user when isCancel is true.
         * @param isTouchOut boolean, true is can't dimiss，false is can dimiss
         */
        private fun showDialog(
            context: Context,
            message: String?,
            isTouchOut: Boolean,
            isCancel: Boolean
        ) {
            if (context is Activity) {
                if (context.isFinishing) {
                    return
                }
            }
            if (loadDialog != null && loadDialog!!.isShowing) {
                return
            }
            loadDialog = BaseLoadingDialog(context, isTouchOut, isCancel, message)
            loadDialog!!.show()
        }

        /**
         * dismiss the mDialogTextView
         */
        fun dismiss(context: Context?) {
            try {
                if (context is Activity) {
                    if (context.isFinishing) {
                        loadDialog = null
                        return
                    }
                }
                if (loadDialog != null && loadDialog!!.isShowing) {
                    val loadContext = loadDialog!!.context
                    if (loadContext != null && loadContext is Activity) {
                        if (loadContext.isFinishing) {
                            loadDialog = null
                            return
                        }
                    }
                    loadDialog!!.dismiss()
                    loadDialog = null
                }
            } catch (e: Exception) {
                e.printStackTrace()
                loadDialog = null
            }
        }
    }

    /**
     * the LoadDialog constructor
     *
     * @param ctx        Context
     * @param isTouchOut boolean : true-->返回不消失，false-->按返回dismiss
     * @param tipMsg     String
     */
    init {
        this.tipMsg = tipMsg!!
        //        this.getContext().setTheme(android.R.style.Theme_InputMethod);
        setContentView(R.layout.base_loading)
        if (!TextUtils.isEmpty(this.tipMsg)) {
            mShowMessage = findViewById<View>(R.id.show_message) as TextView
            mShowMessage!!.visibility = View.VISIBLE
            mShowMessage!!.text = this.tipMsg
        }
        /* 点击返回按钮是否dismiss */this.isCancel = isCancel
        /* 点击外部是否dismiss */setCanceledOnTouchOutside(isTouchOut)
        val window = window
        val attributesParams = window!!.attributes
        attributesParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND
        //        attributesParams.dimAmount = 0.5f;
        attributesParams.dimAmount = 0f //背景透明度
        window.attributes = attributesParams
        window.setBackgroundDrawableResource(android.R.color.transparent) // 一句话搞定
        window.setLayout(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
    }
}