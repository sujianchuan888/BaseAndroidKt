package com.sjc.baseAndroid.weight

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.view.ViewGroup
import com.sjc.baseAndroid.R

class XFlowLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ViewGroup(context, attrs, defStyleAttr) {
    private val TAG = XFlowLayout::class.java.name

    // item间距
    private val dividerWidth: Int

    // 行间距
    private val rowHeight: Int

    // 默认间距
    private val defDW = dp2px(5f)
    private val defRH = dp2px(5f)

    // 子view数量
    private var mChildCount = 0

    // 边距
    private var padLeft = 0
    private var padRight = 0
    private var padTop = 0
    private var padBottom = 0

    /**
     * 摆放原理：按行摆放，循环遍历子View，当子View叠加宽度至一行时宽度重新变为左padding间距，
     * 高度叠加当前行的item高度
     * 进行换行时总高度=当前所有行高度+自定义行间距，然后以此类推叠加
     */
    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        // 子View的左边距和上边距
        var itemWidth = padLeft
        var itemHeight = padTop
        // 记录上一个子View的高度值，当前行所有子view宽度+下一个(i+1)子View的宽度超过一行时，则高度叠加
        var tempHeight = 0
        for (i in 0 until mChildCount) {
            val child = getChildAt(i)
            // 计算当前行总宽度+当前下标子view宽度是否超过父容器行宽度，是则换行，否则直接进行layout摆放
            val childW = child.measuredWidth
            val childH = child.measuredHeight
            // 先计算出当前行的view宽度 + 当前子View宽度，是否超过当前行所剩余的宽度
            var fakeW = itemWidth + childW
            if (fakeW >= measuredWidth - padRight) {
                // 宽度占满一行，进行换行
                if (i != 0) {
                    itemHeight += rowHeight + tempHeight
                }
                itemWidth = padLeft
                fakeW = itemWidth + childW
            }
            child.layout(itemWidth, itemHeight, fakeW, itemHeight + childH)
            itemWidth += childW + dividerWidth
            tempHeight = childH
        }
    }

    /**
     * 测量原理：
     * 当宽度为WrapContent时，则取所有子View里面宽度最长的一个作为总宽度，如果childCount=1，则直接返回子view的宽度 + 左右间距
     * 当高度为WrapContent时，则高度=：计算出所有子View能够摆放的行数 * （行高度） +（行数-1） * 行间距 + 上下间距，如果childCount=1，同上
     */
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var width = MeasureSpec.getSize(widthMeasureSpec)
        var height = MeasureSpec.getSize(heightMeasureSpec)
        val wMode = MeasureSpec.getMode(widthMeasureSpec)
        val hMode = MeasureSpec.getMode(heightMeasureSpec)
        padLeft = paddingLeft
        padRight = paddingRight
        padTop = paddingTop
        padBottom = paddingBottom
        mChildCount = childCount
        measureChildren(widthMeasureSpec, heightMeasureSpec)
        if (mChildCount == 0) {
            setMeasuredDimension(0, 0)
            return
        }
        width = measureWidth(width, wMode)
        height = measureHeight(width, height, hMode)
        setMeasuredDimension(width, height)
    }

    /**
     * 测量宽度
     */
    private fun measureWidth(width: Int, wMode: Int): Int {
        return when (wMode) {
            MeasureSpec.EXACTLY -> {
                Log.d(TAG, "$width---EXACTLY")
                width
            }
            MeasureSpec.AT_MOST -> {
                Log.d(TAG, "$width---AT_MOST")
                Log.d(TAG, "$width---UNSPECIFIED")
                calculateWidth(width)
            }
            MeasureSpec.UNSPECIFIED -> {
                Log.d(TAG, "$width---UNSPECIFIED")
                calculateWidth(width)
            }
            else -> width
        }
    }

    /**
     * 测量高度
     */
    private fun measureHeight(width: Int, height: Int, hMode: Int): Int {
        return when (hMode) {
            MeasureSpec.EXACTLY -> {
                Log.d(TAG, "$width---EXACTLY")
                height
            }
            MeasureSpec.AT_MOST -> {
                Log.d(TAG, "$width---AT_MOST")
                Log.d(TAG, "$width---UNSPECIFIED")
                calculateHeight(width, height)
            }
            MeasureSpec.UNSPECIFIED -> {
                Log.d(TAG, "$width---UNSPECIFIED")
                calculateHeight(width, height)
            }
            else -> height
        }
    }

    /**
     * 计算高度为wrap_content
     * 计算child所占的总行数 * 行宽 + (行数 - 1) * 行间距 + 上下间距
     *
     * @param width  初步测量得到的宽度
     * @param height 初步测量得到的高度
     */
    private fun calculateHeight(width: Int, height: Int): Int {
        val useableWidth = width - padLeft - padRight
        return if (mChildCount == 1) {
            val singleHeight = getChildAt(0).measuredHeight + (padTop + padBottom)
            if (height == 0) {
                singleHeight
            } else Math.min(height, singleHeight)
        } else {
            // 对每个view测量算出行数
            var itemWidth = 0
            var line = 1
            for (i in 0 until mChildCount) {
                val child = getChildAt(i)
                val childW = child.measuredWidth
                val fakeW = itemWidth + childW
                if (fakeW >= useableWidth) {
                    // 行数+1
                    itemWidth = 0
                    itemWidth += childW + dividerWidth
                    line++
                } else {
                    // 未占满一行，行宽度+=子view宽度
                    itemWidth += childW
                    if (i != mChildCount - 1) {
                        itemWidth += dividerWidth
                    }
                }
            }
            line = Math.min(line, mChildCount)
            //  高度=子view高度 * line + 行间距 * (line - 1) + 上下间距
            getChildAt(0).measuredHeight * line + rowHeight * (line - 1) + padTop + padBottom
        }
    }

    /**
     * 计算宽度为wrap_content
     * 求出child中宽最大的，得到最终宽度
     *
     * @param width 初步测量得到的宽度
     */
    private fun calculateWidth(width: Int): Int {
        return if (mChildCount == 1) {
            val singleWidth = getChildAt(0).measuredWidth + (padLeft + padRight)
            if (width == 0) {
                singleWidth
            } else Math.min(width, singleWidth)
        } else {
            var result = 0
            for (i in 0 until mChildCount) {
                val childWidth = getChildAt(i).measuredWidth
                if (result < childWidth) {
                    result = childWidth
                }
            }
            Math.min(width, result + (padLeft + padRight))
        }
    }

    /**
     * DP转PX
     */
    fun dp2px(dpVal: Float): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, dpVal, context.resources
                .displayMetrics
        ).toInt()
    }

    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.SelectFlowLayout)
        dividerWidth =
            typedArray.getDimension(R.styleable.SelectFlowLayout_hor_divider_width, defDW.toFloat())
                .toInt()
        rowHeight =
            typedArray.getDimension(R.styleable.SelectFlowLayout_hor_row_height, defRH.toFloat())
                .toInt()
        typedArray.recycle()
    }
}