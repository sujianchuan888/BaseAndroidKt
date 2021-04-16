package com.sjc.baseAndroid.utils

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.os.Message
import com.google.gson.Gson
import com.sjc.baseAndroid.KtBaseApplication
import org.json.JSONObject
import java.lang.ref.WeakReference


class Share {

    companion object {

        val USER: String = "user"
        var STATE_USER = "state"

        fun getUid(context: Context): Int {
            return context.getSharedPreferences(USER, Context.MODE_PRIVATE).getInt("uid", 0)

        }

        fun getCityList(context: Context): String? {
            return context.getSharedPreferences(USER, Context.MODE_PRIVATE).getString("city", "")

        }

        fun setList(context: Context, cityStr: String) {
            val edit = context.getSharedPreferences(USER, Context.MODE_PRIVATE).edit()
            edit.putString("city", cityStr)
            edit.apply()
        }

        /**
         * 保存用户uid
         */
        fun saveUid(context: Context, uid: Int) {
            val edit = context.getSharedPreferences(USER, Context.MODE_PRIVATE).edit()
            edit.putInt("uid", uid)
            edit.apply()
        }


        fun getUserHead(context: Context): String {
            return context.getSharedPreferences(USER, Context.MODE_PRIVATE).getString("head", "")
                .toString()

        }

        /**
         * 保存用户头像
         */
        fun saveUserHead(context: Context, uid: String) {
            val edit = context.getSharedPreferences(USER, Context.MODE_PRIVATE).edit()
            edit.putString("head", uid)
            edit.apply()
        }

        fun getAccount(context: Context): String {
            return context.getSharedPreferences(USER, Context.MODE_PRIVATE).getString("account", "")
                .toString()

        }

        /**
         * 保存用户电话号码
         */
        fun saveAccount(context: Context, uid: String) {
            val edit = context.getSharedPreferences(USER, Context.MODE_PRIVATE).edit()
            edit.putString("account", uid)
            edit.apply()
        }

        fun getKefu(context: Context): String {
            return context.getSharedPreferences(USER, Context.MODE_PRIVATE).getString("kefu", "")
                .toString()

        }

        /**
         * 保存客服电话
         */
        fun saveKefu(context: Context, uid: String) {
            val edit = context.getSharedPreferences(USER, Context.MODE_PRIVATE).edit()
            edit.putString("kefu", uid)
            edit.apply()
        }

        fun getCardName(context: Context): String {
            return context.getSharedPreferences(USER, Context.MODE_PRIVATE)
                .getString("cardname", "")
                .toString()

        }

        /**
         * 保存银行卡信息
         */
        fun saveCardName(context: Context, uid: String) {
            val edit = context.getSharedPreferences(USER, Context.MODE_PRIVATE).edit()
            edit.putString("cardname", uid)
            edit.apply()
        }

        fun getCardNum(context: Context): String {
            return context.getSharedPreferences(USER, Context.MODE_PRIVATE).getString("cardnum", "")
                .toString()

        }

        /**
         * 保存银行卡信息
         */
        fun saveCardNum(context: Context, uid: String) {
            val edit = context.getSharedPreferences(USER, Context.MODE_PRIVATE).edit()
            edit.putString("cardnum", uid)
            edit.apply()
        }

        fun getFirst(context: Context): String {
            return context.getSharedPreferences(USER, Context.MODE_PRIVATE).getString("first", "")
                .toString()

        }

        /**
         * 保存客服电话
         */
        fun saveFirst(context: Context, uid: String) {
            val edit = context.getSharedPreferences(USER, Context.MODE_PRIVATE).edit()
            edit.putString("first", uid)
            edit.apply()
        }

        fun getQQ(context: Context): String =
            context.getSharedPreferences(USER, Context.MODE_PRIVATE).getString(
                "qq", ""
            ).toString()

        /**
         * QQ绑定状态
         */
        fun saveQQ(context: Context, token: String) {
            val edit = context.getSharedPreferences(USER, Context.MODE_PRIVATE).edit()
            edit.putString("qq", token)
            edit.apply()
        }

        fun getBalance(context: Context): String =
            context.getSharedPreferences(USER, Context.MODE_PRIVATE).getString(
                "Balance", ""
            ).toString()

        /**
         * QQ绑定状态
         */
        fun saveBalance(context: Context, token: String) {
            val edit = context.getSharedPreferences(USER, Context.MODE_PRIVATE).edit()
            edit.putString("Balance", token)
            edit.apply()
        }

        fun getWei(context: Context): String =
            context.getSharedPreferences(USER, Context.MODE_PRIVATE).getString(
                "weixin", ""
            ).toString()

        /**
         * 微信绑定状态
         */
        fun saveWei(context: Context, token: String) {
            val edit = context.getSharedPreferences(USER, Context.MODE_PRIVATE).edit()
            edit.putString("weixin", token)
            edit.apply()
        }

        fun getToken(context: Context): String =
            context.getSharedPreferences(USER, Context.MODE_PRIVATE).getString(
                "token",
                ""
            ).toString()

        /**
         * 保存用户token
         */
        fun saveToken(context: Context, token: String) {
            val edit = context.getSharedPreferences(USER, Context.MODE_PRIVATE).edit()
            edit.putString("token", token)
            edit.apply()
        }

        /**
         * 清除uid和token数据
         */
        fun clearUidOrToken(context: Context) {
            val edit = context.getSharedPreferences(USER, Context.MODE_PRIVATE).edit()
            edit.remove("token")
            edit.remove("uid")
            edit.apply()

        }

        /**
         * 是否是分享 或者微信登录   1 为分享  0 为登录
         */
        fun saveIsShareOrLogin(context: Context, num: Int) {
            val edit = context.getSharedPreferences(USER, Context.MODE_PRIVATE).edit()
            edit.putInt("shareOrLogin", num)
            edit.apply()

        }

        /**
         *  1 为分享   其他为登录
         */
        fun getShareOrLogin(context: Context): Int {
            return context.getSharedPreferences(USER, Context.MODE_PRIVATE)
                .getInt("shareOrLogin", 0)

        }

        /**
         * 邀请码，从扫描二维码中获取，用于注册
         */
        // TODO: 2020/11/17 wwdeng
        fun saveInviteCode(context: Context, code: String) {//保存邀请码
            val edit = context.getSharedPreferences(USER, Context.MODE_PRIVATE).edit()
            edit.putString("inviteCode", code)
            edit.apply()
        }

        fun getInviteCode(context: Context): String {//获取邀请码
            return context.getSharedPreferences(USER, Context.MODE_PRIVATE)
                .getString("inviteCode", "").toString()
        }

        /**
         * 保存用户昵称
         */
        // TODO: 2020/12/1 wwdeng
        fun saveNickName(context: Context, nickName: String) {//保存用户昵称
            val edit = context.getSharedPreferences(USER, Context.MODE_PRIVATE).edit()
            edit.putString("nickName", nickName)
            edit.apply()
        }

        fun getNickName(context: Context): String {//获取用户昵称
            return context.getSharedPreferences(USER, Context.MODE_PRIVATE)
                .getString("nickName", "").toString()
        }


        /**
         * 保存用户是否是代理
         */
        // TODO: 2020/12/1 wwdeng
        fun saveUserProxy(context: Context, nickName: String) {
            val edit = context.getSharedPreferences(USER, Context.MODE_PRIVATE).edit()
            edit.putString("nickName", nickName)
            edit.apply()
        }

        fun getUserProxy(context: Context): String {
            return context.getSharedPreferences(USER, Context.MODE_PRIVATE)
                .getString("nickName", "").toString()
        }

        /**
         * 保存用户类型
         * (1:普通用户，2:VIP用户，3:校长，4:院长，5:教授)
         */
        // TODO: 2020/12/17  wwdeng
        fun saveUserType(context: Context, userType: Int) {//保存用户类型
            val edit = context.getSharedPreferences(USER, Context.MODE_PRIVATE).edit()
            edit.putInt("userType", userType)
            edit.apply()
        }

        fun getUserType(context: Context): Int {//获取用户类型
            return context.getSharedPreferences(USER, Context.MODE_PRIVATE).getInt("userType", 1)
        }

        // TODO: 2020/12/25 wwdeng 保存分享到朋友圈的连接地址
        fun saveShareWXTimelineUrl(context: Context, shareUrl: String) {//保存用户昵称
            val edit = context.getSharedPreferences(USER, Context.MODE_PRIVATE).edit()
            edit.putString("shareUrl", shareUrl)
            edit.apply()
        }

        fun getShareWXTimelineUrl(context: Context): String {//获取分享到朋友圈的连接地址
            return context.getSharedPreferences(USER, Context.MODE_PRIVATE)
                .getString("shareUrl", "www.baidu.com").toString()
        }


    }


}