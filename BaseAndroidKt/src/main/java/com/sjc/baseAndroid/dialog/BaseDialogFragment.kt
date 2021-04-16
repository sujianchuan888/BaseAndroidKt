package com.sjc.baseAndroid.dialog

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.sjc.baseAndroid.R

/**
 *
 */
abstract class BaseDialogFragment(var mClick: View.OnClickListener) : DialogFragment() {
    var mView: View? = null
    var llRoot: View? = null
    var window: Window? = null
    var TAG = javaClass.simpleName
    private var isAnimatorRun = false
    var isBottomShow = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    protected abstract fun setContentView(): Int
    protected abstract fun initView()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mView = inflater.inflate(setContentView(), null)
        llRoot = mView!!.findViewById(R.id.ll_root)
        window = dialog!!.window
        window!!.requestFeature(Window.FEATURE_NO_TITLE)
        window!!.setGravity(Gravity.CENTER)
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog!!.setOnKeyListener(DialogInterface.OnKeyListener { dialog, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                when (event.action) {
                    KeyEvent.ACTION_DOWN -> {
                        if (!isAnimatorRun) {
                            startAnimation(0, llRoot!!.getHeight(), true)
                        }
                        return@OnKeyListener true
                    }
                }
            }
            false
        })
        initView()
        if (isBottomShow) {
            llRoot!!.post(Runnable { startAnimation(llRoot!!.getHeight(), 0, false) })
        }
        return mView
    }

    override fun onStart() {
        super.onStart()
        window!!.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    override fun show(manager: FragmentManager, tag: String?) {
        if (manager != null && (dialog == null || !dialog!!.isShowing) && !this.isAdded) { //判断是否已经显示该弹窗
            try { //此处可try可不try，目前本人使用中没再抛出过异常，但是try下更保险
                val ft = manager.beginTransaction()
                ft.add(this, tag)
                ft.commitAllowingStateLoss()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun dismiss() {
        if (dialog != null && dialog!!.isShowing) { //判断是否已经显示该弹窗
            dismissAllowingStateLoss()
        }
    }

    fun startAnimation(start: Int, end: Int, isClose: Boolean) {
        val animatorSet = AnimatorSet() //组合动画
        val scaleX = ObjectAnimator.ofFloat(llRoot, "scaleX", 0f, 1f)
        val scaleY = ObjectAnimator.ofFloat(llRoot, "scaleY", 0f, 1f)
        scaleX.duration = 50
        scaleY.duration = 50
        animatorSet.duration = 50
        animatorSet.interpolator = DecelerateInterpolator()
        animatorSet.play(scaleX).with(scaleY) //两个动画同时开始
        //        animatorSet.start();
//
        val anim = ObjectAnimator.ofFloat(llRoot, "translationY", start.toFloat(), end.toFloat())
        anim.duration = 250
        anim.interpolator = AccelerateInterpolator()
        anim.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {
                isAnimatorRun = true
            }

            override fun onAnimationEnd(animation: Animator) {
                isAnimatorRun = false
                if (isClose) {
                    dismiss()
                }
            }

            override fun onAnimationCancel(animation: Animator) {}
            override fun onAnimationRepeat(animation: Animator) {}
        })
        anim.start()
    }
}