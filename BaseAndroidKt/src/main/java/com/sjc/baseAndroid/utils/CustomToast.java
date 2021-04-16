package com.sjc.baseAndroid.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

import com.sjc.baseAndroid.R;


/**
 * 自定义通知
 */

public class CustomToast {

    private static TextView mTextView;
    private static ImageView mImageView;

    /**
     * @param context 上下文
     * @param gravity 位置
     * @param resId   显示图片的id
     * @param message 显示的文字
     */
    public static void showToast(Context context, int gravity, int resId, String message) {
        if (context == null){
            return;
        }
        //加载Toast布局
        View toastView = LayoutInflater.from(context).inflate(R.layout.toast, null);
        //初始化布局控件
        mTextView = toastView.findViewById(R.id.message);
        mImageView = toastView.findViewById(R.id.imageView);
        //为控件设置属性
        mTextView.setText(message);

        if (resId != 0) {

            mImageView.setVisibility(View.VISIBLE);
            mImageView.setImageDrawable(ActivityCompat.getDrawable(context, resId));

        } else {
            mImageView.setVisibility(View.GONE);
        }

        /// Toast的初始化
        Toast toastStart = new Toast(context);

        toastStart.setGravity(gravity, 0, 0);
        toastStart.setDuration(Toast.LENGTH_SHORT);
        toastStart.setView(toastView);
        toastStart.show();

    }

}
