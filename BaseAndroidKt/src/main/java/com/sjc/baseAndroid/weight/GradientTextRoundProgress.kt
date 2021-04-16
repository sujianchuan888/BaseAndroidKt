package com.sjc.baseAndroid.weight

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.sjc.baseAndroid.R

/**
 * 配文字环形进度条
 */
class GradientTextRoundProgress @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : View(context, attrs, defStyle) {
    private val paint // 画笔对象的引用
            : Paint
    private val textPaint // 值文字画笔对象的引用
            : Paint
    private val tipsPaint // 标题文字画笔对象的引用
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
    private val textTips // 文字内容
            : String?
    private val tiptextColor // 标题字符串的颜色
            : Int
    private val textColor // 中间进度百分比的字符串的颜色
            : Int
    private val tiptextSize // 标题字符串的字体大小
            : Float
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
    private val useCustomFont // 是否使用自定义字体
            : Boolean
    private var progress // 当前进度
            = 0

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
        canvas.drawArc(oval, startAngle.toFloat(), sweepAngle.toFloat(), false, paint)

        // step3 画文字指示
        textPaint.strokeWidth = 0f
        textPaint.color = textColor
        textPaint.style = Paint.Style.FILL
        textPaint.textSize = textSize
        tipsPaint.strokeWidth = 0f
        tipsPaint.color = tiptextColor
        tipsPaint.style = Paint.Style.FILL
        tipsPaint.textSize = tiptextSize
        // 计算百分比
        val percent = (progress.toFloat() / max.toFloat() * 100).toInt()
        if (textShow && text != null && text.length > 0 && percent >= 0) {
            // 3.1 画文字
            tipsPaint.typeface = Typeface.DEFAULT // 设置为默认字体
            val textWidth = tipsPaint.measureText(textTips) // 测量字体宽度
            canvas.drawText(textTips!!, centerX - textWidth / 2, centerX + textSize + 5, paint)


            // 3.2 画百分比
            textPaint.textSize = numSize
            if (useCustomFont) {
                textPaint.typeface =
                    com.sjc.baseAndroid.weight.MediumManager.setFont(context, 4) // 设置字体
            } else {
                textPaint.typeface = Typeface.DEFAULT_BOLD // 设置为加粗默认字体
            }
            val numWidth = textPaint.measureText("$percent%") // 测量字体宽度，我们需要根据字体的宽度设置在圆环中间
            canvas.drawText(
                "$percent%",
                centerX - numWidth / 2,
                centerX.toFloat(),
                paint
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
    fun getProgress(): Int {
        return progress
    }

    /**
     * 设置进度，此为线程安全控件
     *
     * @param progress 进度值
     */
    @Synchronized
    fun setProgress(progress: Int) {
        var progress = progress
        require(progress >= 0) { "progress not less than 0" }
        if (progress > max) {
            progress = max
        }
        this.progress = progress
        // 刷新界面调用postInvalidate()能在非UI线程刷新
        postInvalidate()
    }

    init {
        paint = Paint()
        textPaint = Paint()
        tipsPaint = Paint()
        // 读取自定义属性的值
        val mTypedArray = context.obtainStyledAttributes(attrs, R.styleable.TextRoundProgress)

        // 获取自定义属性和默认值
        roundColor = mTypedArray.getColor(R.styleable.TextRoundProgress_trp_roundColor, Color.RED)
        roundWidth = mTypedArray.getDimension(R.styleable.TextRoundProgress_trp_roundWidth, 5f)
        progressColor =
            mTypedArray.getColor(R.styleable.TextRoundProgress_trp_progressColor, Color.GREEN)
        progressWidth =
            mTypedArray.getDimension(R.styleable.TextRoundProgress_trp_progressWidth, roundWidth)
        text = mTypedArray.getString(R.styleable.TextRoundProgress_trp_text)
        textTips = mTypedArray.getString(R.styleable.TextRoundProgress_trp_text)
        textColor = mTypedArray.getColor(R.styleable.TextRoundProgress_trp_textColor, Color.GREEN)
        tiptextColor =
            mTypedArray.getColor(R.styleable.TextRoundProgress_trp_tips_textColor, Color.GREEN)
        textSize = mTypedArray.getDimension(R.styleable.TextRoundProgress_trp_textSize, 11f)
        tiptextSize = mTypedArray.getDimension(R.styleable.TextRoundProgress_trp_tips_textSize, 11f)
        numSize = mTypedArray.getDimension(R.styleable.TextRoundProgress_trp_numSize, 14f)
        max = mTypedArray.getInteger(R.styleable.TextRoundProgress_trp_max, 100)
        startAngle = mTypedArray.getInt(R.styleable.TextRoundProgress_trp_startAngle, 90)
        textShow = mTypedArray.getBoolean(R.styleable.TextRoundProgress_trp_textShow, true)
        useCustomFont =
            mTypedArray.getBoolean(R.styleable.TextRoundProgress_trp_userCustomFont, false)
        mTypedArray.recycle()
    }
}