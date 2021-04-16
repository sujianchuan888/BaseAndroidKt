package com.sjc.baseAndroid.login

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.sjc.baseAndroid.R
import com.sjc.baseAndroid.BR
import com.sjc.baseAndroid.base.AppViewModelFactory
import com.sjc.baseAndroid.databinding.ActivityLoginBinding
import me.goldze.mvvmhabit.base.BaseActivity

class LoginActivity : BaseActivity<ActivityLoginBinding, LoginViewModel>() {
    override fun initContentView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_login
    }

    override fun initVariableId(): Int {
        return BR.data
    }

    override fun initViewModel(): LoginViewModel {
        return ViewModelProvider(this, AppViewModelFactory.getInstance(application)!!)[LoginViewModel::class.java]
    }

    override fun initData() {
        super.initData()
        viewModel.mcontext = this
    }

    override fun initViewObservable() {
        super.initViewObservable()

    }
}