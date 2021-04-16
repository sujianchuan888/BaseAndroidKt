package com.sjc.baseAndroid.login

import android.app.Application
import android.content.Context
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import com.example.myapplication.http.AppRepository
import com.sjc.baseAndroid.base.BaseBean
import com.sjc.baseAndroid.base.KtBaseViewModel
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableObserver
import me.goldze.mvvmhabit.base.BaseViewModel
import me.goldze.mvvmhabit.binding.command.BindingAction
import me.goldze.mvvmhabit.binding.command.BindingCommand
import me.goldze.mvvmhabit.utils.RxUtils
import me.goldze.mvvmhabit.utils.ToastUtils
import okhttp3.MediaType
import okhttp3.RequestBody
import org.json.JSONObject


class LoginViewModel(application: Application, model: AppRepository?) : KtBaseViewModel(
    application,
    model!!
){
    //用户名的绑定
    var mobile = ObservableField<String>("")
    var v_code = ObservableField<String>("")
    var clearBtnVisibility = ObservableInt()
    var loginOnClickCommand: BindingCommand<*> = BindingCommand<Any?>(BindingAction { login() })
    var clearTel: BindingCommand<*> = BindingCommand<Any?>(BindingAction { mobile.set("") })
    var sendCodeOnClickCommand: BindingCommand<*> = BindingCommand<Any?>(BindingAction { sendCode() })
    var proxyOnClickCommand: BindingCommand<*> = BindingCommand<Any?>(BindingAction { })
    var serviceOnClickCommand: BindingCommand<*> = BindingCommand<Any?>(BindingAction { })

    //清除用户名的点击事件, 逻辑从View层转换到ViewModel层
    var clearUserNameOnClickCommand: BindingCommand<*> = BindingCommand<Any?>(BindingAction { })
    //用户名输入框焦点改变的回调事件
    var onFocusChangeCommand = BindingCommand<Boolean> { hasFocus ->
        if (hasFocus) {
            clearBtnVisibility.set(View.VISIBLE)
        } else {
            clearBtnVisibility.set(View.INVISIBLE)
        }
    }

    private fun login() {
        val json = JSONObject()
        json.put("mobile", mobile.get().toString())
        json.put("yzm", v_code.get())
        var tos = json.toString()
        val body: RequestBody =
            RequestBody.create(MediaType.parse("application/json; charset=utf-8"), tos)
        model.login(body)!!
            .compose(RxUtils.schedulersTransformer()) // 线程调度
            .compose(RxUtils.exceptionTransformer()) // 网络错误的异常转换, 这里可以换成自己的ExceptionHandle
            .doOnSubscribe(this) // 请求与ViewModel周期同步
            .doOnSubscribe { disposable -> showDialog("正在请求...") }
            .subscribe(object : DisposableObserver<Any>() {
                override fun onNext(t: Any) {


                }

                override fun onError(e: Throwable) {
                    Log.e("sendCode","onError : " +e)
                }

                override fun onComplete() {
                }
            })
    }
    private fun sendCode() {
        val json = JSONObject()
        json.put("mobile", mobile.get().toString())
        json.put("type", "1")
        var tos = json.toString()
        val body: RequestBody =
            RequestBody.create(MediaType.parse("application/json; charset=utf-8"), tos)
        showLoading("")
        model.sendCode(body)!!
            .compose(RxUtils.schedulersTransformer()) // 线程调度
            .compose(RxUtils.exceptionTransformer()) // 网络错误的异常转换, 这里可以换成自己的ExceptionHandle
            .doOnSubscribe(this) // 请求与ViewModel周期同步
            .doOnSubscribe { disposable -> showDialog("正在请求...") }
            .subscribe(object : DisposableObserver<Any>() {
                override fun onNext(t: Any) {
                    var basebean = t as BaseBean<String>
                    ToastUtils.showShort(basebean.msg)
                    if (basebean.code == 200){

                    }
                }

                override fun onError(e: Throwable) {
                    Log.e("sendCode","onError : " +e)
                    hideLoading()
                }

                override fun onComplete() {
                    hideLoading()
                }
            })
    }


}