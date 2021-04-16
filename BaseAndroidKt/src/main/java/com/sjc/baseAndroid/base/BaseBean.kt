package com.sjc.baseAndroid.base

import com.google.gson.annotations.SerializedName

 class BaseBean<T> {
    public val code = 0

     public val msg: String? = null

     public val time: String? = null

     public val data: T? = null
}