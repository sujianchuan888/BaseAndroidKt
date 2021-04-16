package com.sjc.baseAndroid.base

import android.app.Application
import android.content.Context
import com.example.myapplication.http.AppRepository
import com.sjc.baseAndroid.dialog.BaseLoadingDialog
import me.goldze.mvvmhabit.base.BaseViewModel


open class KtBaseViewModel(application: Application, model: AppRepository?) :
    BaseViewModel<AppRepository>(
        application,
        model!!
    ) {

    var mcontext: Context? = null
    /**
     * 显示加载对话框
     * 系统返回和外部都取消
     * @param msg
     */

     fun showLoading(msg: String?) {
        BaseLoadingDialog.show(mcontext!!, msg!!, true, true)
    }
    fun hideLoading() {
        BaseLoadingDialog.dismiss(mcontext)
    }

}