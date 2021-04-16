package com.example.myapplication.app

import android.annotation.SuppressLint
import android.app.Application
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.app.Injection.provideDemoRepository
import com.example.myapplication.http.AppRepository
import com.example.myapplication.ui.login.LoginViewModel
import com.example.myapplication.ui.main.fragment.FollowViewModel
import com.example.myapplication.ui.register.RegisterViewModel

/**
 * Created by goldze on 2019/3/26.
 */
class AppViewModelFactory private constructor(
    private val mApplication: Application,
    private val mRepository: AppRepository?
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(mApplication, mRepository) as T
        } else if (modelClass.isAssignableFrom(FollowViewModel::class.java)) {
            return FollowViewModel(mApplication, mRepository) as T
        }else if (modelClass.isAssignableFrom(RegisterViewModel::class.java)) {
            return RegisterViewModel(mApplication, mRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        @Volatile
        private var INSTANCE: AppViewModelFactory? = null
        fun getInstance(application: Application): AppViewModelFactory? {
            if (INSTANCE == null) {
                synchronized(AppViewModelFactory::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = AppViewModelFactory(application, provideDemoRepository())
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