package com.example.myapplication.ui.login

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.myapplication.BR
import com.example.myapplication.R
import com.example.myapplication.app.AppApplication
import com.example.myapplication.app.AppViewModelFactory
import com.example.myapplication.app.Constant
import com.example.myapplication.databinding.ActivityLoginBinding
import com.sjc.baseAndroid.utils.Share
import me.goldze.mvvmhabit.base.BaseActivity

@Route(path = Constant.ROUTE_LOGIN)
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
        Share.getToken(AppApplication.appContext!!)
    }

    override fun initViewObservable() {
        super.initViewObservable()

    }
}