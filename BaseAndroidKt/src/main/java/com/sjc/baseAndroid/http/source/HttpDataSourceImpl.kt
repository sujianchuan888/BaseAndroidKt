package com.example.myapplication.http.source

import com.example.myapplication.http.service.HttpApiService
import com.sjc.baseAndroid.base.BaseBean
import io.reactivex.Observable
import okhttp3.MultipartBody
import okhttp3.RequestBody

/**
 * @Author
 */
class HttpDataSourceImpl private constructor(private val apiService: HttpApiService) :
    HttpDataSource {
    override fun sendCode(json: RequestBody?): Observable<BaseBean<*>?>? {
        return apiService.sendCode(json)
    }

    override fun login(json: RequestBody?): Observable<BaseBean<*>?>? {
        return apiService.login(json)
    }

    override fun uploadFile(filePart: MultipartBody.Part?): Observable<BaseBean<*>?>? {
        return apiService.uploadFile(filePart)
    }

    companion object {
        @Volatile
        private var INSTANCE: HttpDataSourceImpl? = null
        @JvmStatic
        fun getInstance(apiService: HttpApiService): HttpDataSourceImpl? {
            if (INSTANCE == null) {
                synchronized(HttpDataSourceImpl::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = HttpDataSourceImpl(apiService)
                    }
                }
            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}