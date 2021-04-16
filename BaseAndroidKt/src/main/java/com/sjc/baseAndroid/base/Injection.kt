package com.sjc.baseAndroid.base

import com.example.myapplication.http.AppRepository
import com.example.myapplication.http.AppRepository.Companion.getInstance
import com.example.myapplication.http.RetrofitClient
import com.example.myapplication.http.service.HttpApiService
import com.example.myapplication.http.source.HttpDataSource
import com.example.myapplication.http.source.HttpDataSourceImpl.Companion.getInstance

/**
 * 注入全局的数据仓库，可以考虑使用Dagger2。（根据项目实际情况搭建，千万不要为了架构而架构）
 * Created by goldze on 2019/3/26.
 */
object Injection {
    @JvmStatic
    fun provideDemoRepository(): AppRepository? {
        //网络API服务
        val apiService = RetrofitClient.retrofitClient.create(
            HttpApiService::class.java
        )
        //网络数据源
        val httpDataSource: HttpDataSource? = getInstance(apiService)
        //两条分支组成一个数据仓库
        return getInstance(httpDataSource!!)
    }
}