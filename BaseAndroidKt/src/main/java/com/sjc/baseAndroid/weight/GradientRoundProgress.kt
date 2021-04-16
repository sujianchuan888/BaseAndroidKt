package com.sjc.baseAndroid.weight

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.sjc.baseAndroid.R
import com.sjc.baseAndroid.weight.BoldManager.setFont

/**
 * 基础渐变色环形进度条
 */
class GradientRoundProgress @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : View(context, attrs, defStyle) {
    private val paint // 画笔对象的引用
            : Paint
    private val textPaint // 文字画笔对象的引用
            : Paint
    private val roundColor // 圆环的颜色
            : Int
    private val roundWidth // 圆环的宽度
            : Float
    private val progressColor // 圆环进度的颜色
            : Int
    private val progressWidth // 圆环进度的宽度
            : Float
    private val text // 文字内容
            : String?
    private val textColor // 中间进度百分比的字符串的颜色
            : Int
    private val textSize // 中间进度百分比的字符串的字体大小
            : Float
    private val numSize // 中间进度文本大小
            : Float
    private var max // 最大进度
            : Int
    private val startAngle // 进度条起始角度
            : Int
    private val textShow // 是否显示中间的进度
            : Boolean
    private val textRate // 文字是否带%
            : Boolean
    private val useCustomFont // 是否使用自定义字体
            : Boolean
    private val startColor // 渐变色起始色
            : Int
    private val midColor // 渐变色中间色
            : Int
    private val endColor // 渐变色起始色
            : Int
    private var progress // 当前进度
            = 0.0

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val centerX = width / 2 // 获取圆心的x坐标
        val radius = (centerX - roundWidth / 2).toInt() // 圆环的半径

        // step1 画最外层的大圆环
        paint.strokeWidth = roundWidth // 设置圆环的宽度
        paint.color = roundColor // 设置圆环的颜色
        paint.isAntiAlias = true // 消除锯齿
        // 设置画笔样式
        paint.style = Paint.Style.STROKE
        canvas.drawCircle(centerX.toFloat(), centerX.toFloat(), radius.toFloat(), paint) // 画出圆环

        // step2 画圆弧-画圆环的进度
        // 锁画布(为了保存之前的画布状态)
        canvas.save()
        canvas.rotate(startAngle.toFloat(), centerX.toFloat(), centerX.toFloat())
        paint.strokeWidth = progressWidth // 设置画笔的宽度使用进度条的宽度
        paint.color = progressColor // 设置进度的颜色
        val oval = RectF(
            (centerX - radius).toFloat(),
            (centerX - radius).toFloat(),
            (centerX + radius).toFloat(),
            (centerX + radius).toFloat()
        ) // 用于定义的圆弧的形状和大小的界限
        val sweepAngle = 360 * progress / max // 计算进度值在圆环所占的角度
        // 根据进度画圆弧
        paint.shader =
            SweepGradient(centerX as Float, centerX as Float, intArrayOf(startColor, midColor, endColor), null)
        canvas.drawArc(oval, 0f, sweepAngle.toFloat(), false, paint)
        paint.shader = null
        canvas.rotate(-startAngle.toFloat(), centerX.toFloat(), centerX.toFloat())
        canvas.restore()

        // step3 画文字指示
        textPaint.strokeWidth = 0f
        textPaint.color = textColor
        textPaint.style = Paint.Style.FILL
        textPaint.textSize = textSize
        // 计算百分比
        val percent = (progress.toFloat() / max.toFloat() * 100).toInt()
        if (textShow) {
            // 3.1 画文字
            // 3.2 画百分比
            textPaint.textSize = numSize
            textPaint.typeface = setFont(context, 1) // 设置字体
            var str = ""
            str = if (textRate) {
                "$percent%"
            } else {
                percent.toString() + ""
            }
            val numWidth = textPaint.measureText(str) // 测量字体宽度，我们需要根据字体的宽度设置在圆环中间
            canvas.drawText(
                str,
                centerX - numWidth / 2,
                centerX + numWidth / 4,
                textPaint
            ) // 画出进度百分比
        }
    }

    /**
     * 设置进度的最大值
     *
     * 根据需要，最大值一般设置为100，也可以设置为1000、10000等
     *
     * @param max int最大值
     */
    @Synchronized
    fun setMax(max: Int) {
        require(max >= 0) { "max not less than 0" }
        this.max = max
    }

    /**
     * 获取进度
     *
     * @return int 当前进度值
     */
    @Synchronized
    fun getProgress(): Double {
        return progress
    }

    /**
     * 设置进度，此为线程安全控件
     *
     * @param progress 进度值
     */
    @Synchronized
    fun setProgress(progress: Double) {
        var progress = progress
        require(progress >= 0) { "progress not less than 0" }
        if (progress > max) {
            progress = max.toDouble()
        }
        this.progress = progress
        // 刷新界面调用postInvalidate()能在非UI线程刷新
        postInvalidate()
    }

    init {
        paint = Paint()
        textPaint = Paint()

        // 读取自定义属性的值
        val mTypedArray = context.obtainStyledAttributes(attrs, R.styleable.GradientRoundProgress)

        // 获取自定义属性和默认值
        roundColor =
            mTypedArray.getColor(R.styleable.GradientRoundProgress_grp_roundColor, Color.RED)
        roundWidth = mTypedArray.getDimension(R.styleable.GradientRoundProgress_grp_roundWidth, 5f)
        progressColor =
            mTypedArray.getColor(R.styleable.GradientRoundProgress_grp_progressColor, Color.GREEN)
        progressWidth = mTypedArray.getDimension(
            R.styleable.GradientRoundProgress_grp_progressWidth,
            roundWidth
        )
        text = mTypedArray.getString(R.styleable.GradientRoundProgress_grp_text)
        textColor =
            mTypedArray.getColor(R.styleable.GradientRoundProgress_grp_textColor, Color.GREEN)
        textSize = mTypedArray.getDimension(R.styleable.GradientRoundProgress_grp_textSize, 11f)
        numSize = mTypedArray.getDimension(R.styleable.GradientRoundProgress_grp_numSize, 14f)
        max = mTypedArray.getInteger(R.styleable.GradientRoundProgress_grp_max, 100)
        startAngle = mTypedArray.getInt(R.styleable.GradientRoundProgress_grp_startAngle, 90)
        textShow = mTypedArray.getBoolean(R.styleable.GradientRoundProgress_grp_textShow, true)
        textRate = mTypedArray.getBoolean(R.styleable.GradientRoundProgress_grp_textRate, true)
        useCustomFont =
            mTypedArray.getBoolean(R.styleable.GradientRoundProgress_grp_userCustomFont, false)
        startColor =
            mTypedArray.getColor(R.styleable.GradientRoundProgress_grp_startColor, Color.GREEN)
        midColor = mTypedArray.getColor(R.styleable.GradientRoundProgress_grp_midColor, Color.GREEN)
        endColor = mTypedArray.getColor(R.styleable.GradientRoundProgress_grp_endColor, Color.GREEN)
        mTypedArray.recycle()
    }
}