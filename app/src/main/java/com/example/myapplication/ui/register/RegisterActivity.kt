package com.example.myapplication.ui.register

import android.os.Bundle
import android.os.Message
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.myapplication.BR
import com.example.myapplication.R
import com.example.myapplication.app.Constant
import com.example.myapplication.databinding.ActivityRegisterBinding
import com.example.myapplication.ui.login.LoginViewModel
import com.sjc.baseAndroid.base.ProActivity
@Route(path = Constant.ROUTE_REGISTER)
class RegisterActivity : ProActivity<ActivityRegisterBinding, RegisterViewModel>() {

    override fun initView() {
    }

    override fun initListener() {
    }

    override fun initContentView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_register
    }

    override fun initVariableId(): Int {
        return BR.data
    }


}