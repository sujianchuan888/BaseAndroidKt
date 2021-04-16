package com.sjc.baseAndroid.weight

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.viewpager.widget.ViewPager

class NoScrollViewPager : ViewPager {
    constructor(context: Context?, attrs: AttributeSet?) : super(
        context!!, attrs
    ) {
    }

    constructor(context: Context?) : super(context!!) {}

    /**
     * 事件不处理触摸事件，返回false
     */
    override fun onTouchEvent(ev: MotionEvent): Boolean {
        return false
    }

    /**
     * false：不拦截儿子的触摸事件
     */
    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        return false
    } //
    //    @Override
    //    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    //
    //        int height = 0;
    //        for (int i = 0; i < getChildCount(); i++) {
    //            View child = getChildAt(i);
    //            child.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
    //            int h = child.getMeasuredHeight();
    //            if (h > height) height = h;
    //        }
    //
    //        heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
    //
    //        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    //    }
}