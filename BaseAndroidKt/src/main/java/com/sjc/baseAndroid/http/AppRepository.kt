package com.example.myapplication.http

import com.example.myapplication.http.source.HttpDataSource
import okhttp3.RequestBody
import okhttp3.MultipartBody
import kotlin.jvm.Volatile
import com.example.myapplication.http.AppRepository
import androidx.annotation.VisibleForTesting
import com.sjc.baseAndroid.base.BaseBean
import io.reactivex.Observable
import me.goldze.mvvmhabit.base.BaseModel

/**
 * MVVM的Model层，统一模块的数据仓库，包含网络数据和本地数据（一个应用可以有多个Repository）
 */
class AppRepository private constructor(private val mHttpDataSource: HttpDataSource) : BaseModel(),
    HttpDataSource {
    override fun sendCode(json: RequestBody?): Observable<BaseBean<*>?>? {
        return mHttpDataSource.sendCode(json)
    }

    override fun login(json: RequestBody?): Observable<BaseBean<*>?>? {
        return mHttpDataSource.login(json)
    }

    override fun uploadFile(filePart: MultipartBody.Part?): Observable<BaseBean<*>?>? {
        return mHttpDataSource.uploadFile(filePart)
    }

    companion object {
        @Volatile
        private var INSTANCE: AppRepository? = null
        @JvmStatic
        fun getInstance(httpDataSource: HttpDataSource): AppRepository? {
            if (INSTANCE == null) {
                synchronized(AppRepository::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = AppRepository(httpDataSource)
                    }
                }
            }
            return INSTANCE
        }

        @VisibleForTesting
        fun destroyInstance() {
            INSTANCE = null
        }
    }
}