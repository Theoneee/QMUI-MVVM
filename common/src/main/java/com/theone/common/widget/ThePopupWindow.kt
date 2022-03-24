package com.theone.common.widget

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.view.animation.Animation
import android.view.animation.TranslateAnimation
import android.widget.PopupWindow

//  ┏┓　　　┏┓
//┏┛┻━━━┛┻┓
//┃　　　　　　　┃
//┃　　　━　　　┃
//┃　┳┛　┗┳　┃
//┃　　　　　　　┃
//┃　　　┻　　　┃
//┃　　　　　　　┃
//┗━┓　　　┏━┛
//    ┃　　　┃                  神兽保佑
//    ┃　　　┃                  永无BUG！
//    ┃　　　┗━━━┓
//    ┃　　　　　　　┣┓
//    ┃　　　　　　　┏┛
//    ┗┓┓┏━┳┓┏┛
//      ┃┫┫　┃┫┫
//      ┗┻┛　┗┻┛
/**
 * @author The one
 * @date 2021-03-29 13:14
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
class ThePopupWindow(context: Context, rootView: View, val anchor: View, content: View) :
    PopupWindow(context),Animation.AnimationListener {


    /**
     * 动画效果样式
     */
    private var mAnimStyle: AnimStyle = AnimStyle.TOP_BOTTOM
        set(value) {
            initAnimalStyle()
            field = value
        }

    /**
     * 进入动画
     */
    private var mEnterAnim: Animation? = null

    /**
     * 退出动画
     */
    private var mExitAnim: Animation? = null

    /**
     * 进出动画默认时间
     */
    var mDefaultTime: Long = 500

    /**
     * 进入动画显示时间
     */
    var mEnterTime: Long = mDefaultTime

    /**
     * 退出动画显示时间
     */
    var mExitTime: Long = mDefaultTime

    /**
     * 动画效果样式
     */
    enum class AnimStyle {
        // 从上到下
        TOP_BOTTOM,

        //从下到上
        BOTTOM_TOP,

        //从做到右
        LEFT_RIGHT,

        //从右到左
        RIGHT_LEFT
    }

    init {
        contentView = content
        setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        height = rootView.height - anchor.height
        width = rootView.width
    }

    /**
     * 显示
     */
    fun show() {
        showAsDropDown(anchor)
        contentView.startAnimation(mEnterAnim)
    }

    /**
     * 隐藏
     */
    fun hide(listener:Animation.AnimationListener = this) {
        mExitAnim?.let {
            it.setAnimationListener(listener)
            contentView.startAnimation(it)
        }
    }

    /**
     * 初始化默认的动画效果
     */
    private fun initAnimalStyle() {
        val enter = arrayOf(0f, 0f, 0f, 0f)
        val exit = arrayOf(0f, 0f, 0f, 0f)
        when (mAnimStyle) {
            AnimStyle.TOP_BOTTOM -> {
                enter[2] = -1f
                exit[3] = -1f
            }
            AnimStyle.BOTTOM_TOP -> {
                enter[2] = 1f
                exit[3] = 1f
            }
            AnimStyle.LEFT_RIGHT -> {
                enter[0] = -1f
                exit[1] = -1f
            }
            else -> {
                enter[0] = 1f
                exit[1] = 1f
            }
        }
        mEnterAnim = getDefaultAnimation(enter).apply { duration = mEnterTime }
        mExitAnim = getDefaultAnimation(exit).apply { duration = mExitTime }
    }

    private fun getDefaultAnimation(anim: Array<Float>): TranslateAnimation {
        return TranslateAnimation(
            TranslateAnimation.RELATIVE_TO_SELF, anim[0],
            TranslateAnimation.RELATIVE_TO_SELF, anim[1],
            TranslateAnimation.RELATIVE_TO_SELF, anim[2],
            TranslateAnimation.RELATIVE_TO_SELF, anim[3]
        )
    }

    override fun onAnimationRepeat(animation: Animation?) {
    }

    override fun onAnimationEnd(animation: Animation?) {
        dismiss()
    }

    override fun onAnimationStart(animation: Animation?) {
    }

}