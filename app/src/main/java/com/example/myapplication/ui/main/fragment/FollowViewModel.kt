package com.example.myapplication.ui.main.fragment

import android.app.Application
import android.content.Context
import android.widget.Toast
import com.example.myapplication.http.AppRepository
import com.example.myapplication.ui.login.LoginActivity
import me.goldze.mvvmhabit.base.BaseViewModel
import me.goldze.mvvmhabit.binding.command.BindingAction
import me.goldze.mvvmhabit.binding.command.BindingCommand

class FollowViewModel(application: Application, model: AppRepository?) :BaseViewModel<AppRepository>(application,model!!){
    var tel = ""
    var mcontext :Context? = null

    var tologinOnClick: BindingCommand<*> = BindingCommand<Any?>(BindingAction { login() })

    private fun login() {
        startActivity(LoginActivity::class.java)
    }


}