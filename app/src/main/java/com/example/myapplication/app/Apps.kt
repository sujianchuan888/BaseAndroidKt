package com.example.myapplication.app

import android.content.Context
import me.goldze.mvvmhabit.base.BaseApplication

class Apps : BaseApplication() {
    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
        apps = this
    }

    companion object {
        /**
         * 获得上下文对象
         *
         * @return
         */
        var appContext: Context? = null
            private set
        private var apps: Apps? = null
        var order_type = ""
        var order_id = ""
        var pay_type = ""
    }
}