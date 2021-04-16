package com.example.myapplication.ui.main.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.BR
import com.example.myapplication.R
import com.example.myapplication.app.AppApplication
import com.example.myapplication.app.AppViewModelFactory
import com.example.myapplication.databinding.FragmentFollowBinding
import me.goldze.mvvmhabit.base.BaseFragment

class FollowFragment4 :BaseFragment<FragmentFollowBinding,FollowViewModel>() {
    override fun initContentView(
        inflater: LayoutInflater?,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): Int {
        return R.layout.fragment_follow4
    }

    override fun initVariableId(): Int {
        return BR.data
    }

    override fun initViewModel(): FollowViewModel {
        return ViewModelProvider(this, AppViewModelFactory.getInstance(AppApplication())!!)[FollowViewModel::class.java]
    }

    override fun initData() {
        super.initData()
        Log.d("Fragment"," 首页初始化 : " + 4)
    }
}