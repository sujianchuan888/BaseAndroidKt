package com.example.myapplication.http.source

import com.sjc.baseAndroid.base.BaseBean
import io.reactivex.Observable
import okhttp3.MultipartBody
import okhttp3.RequestBody

/**
 * @author Xavier Yang
 */
interface HttpDataSource {
    /**
     * 发送验证码
     *
     * @return 登录结果
     */
    fun sendCode(json: RequestBody?): Observable<BaseBean<*>?>?

    /**
     * 登录
     *
     * @return 登录结果
     */
    fun login(json: RequestBody?): Observable<BaseBean<*>?>?

    /**
     * 文件上传接口
     *
     * @param filePart 文件
     * @return 上传结果
     */
    fun uploadFile(filePart: MultipartBody.Part?): Observable<BaseBean<*>?>?
}