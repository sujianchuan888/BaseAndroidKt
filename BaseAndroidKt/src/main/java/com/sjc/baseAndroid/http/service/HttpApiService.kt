package com.example.myapplication.http.service

import retrofit2.http.POST
import okhttp3.RequestBody
import com.sjc.baseAndroid.base.BaseBean
import io.reactivex.Observable
import retrofit2.http.Multipart
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.Part

/**
 * @author Xavier Yang
 */
interface HttpApiService {
    /**
     * 会员登陆接口
     *
     * @return 登录请求结果
     */
    @POST("Account/yzm")
    fun sendCode(@Body json: RequestBody?): Observable<BaseBean<*>?>?

    /**
     * 会员登陆接口
     *
     * @return 登录请求结果
     */
    @POST("Account/yzm_login")
    fun login(@Body json: RequestBody?): Observable<BaseBean<*>?>?

    /**
     * 文件上传接口
     *
     * @param filePart 文件
     * @return 上传结果
     */
    @Multipart
    @POST("/api/common/upload")
    fun uploadFile(@Part filePart: MultipartBody.Part?): Observable<BaseBean<*>?>?
}