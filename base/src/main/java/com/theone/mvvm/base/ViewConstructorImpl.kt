package com.theone.mvvm.base

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintLayout.LayoutParams.PARENT_ID
import androidx.core.content.ContextCompat
import com.qmuiteam.qmui.kotlin.matchParent
import com.qmuiteam.qmui.kotlin.wrapContent
import com.qmuiteam.qmui.skin.QMUISkinHelper
import com.qmuiteam.qmui.widget.QMUITopBarLayout
import com.qmuiteam.qmui.widget.QMUIWindowInsetLayout2
import com.theone.mvvm.R
import java.lang.RuntimeException

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
 * @date 2022-02-24 10:13
 * @describe View构造器
 * @email 625805189@qq.com
 * @remark
 */
open class ViewConstructorImpl(
    context: Context,
    contentFactory: Factory,
    private val base: IQMUI
) : ViewConstructor(context, contentFactory) {

    override fun createRootView(): ViewGroup = QMUIWindowInsetLayout2(context)

    override fun createTopBar(): QMUITopBarLayout? {
        return if (base.showTopBar()) {
            QMUITopBarLayout(context).apply {
                id = R.id.base_topbar
                layoutParams = ConstraintLayout.LayoutParams(matchParent, wrapContent).apply {
                    startToStart = PARENT_ID
                    endToEnd = PARENT_ID
                    topToTop = PARENT_ID
                }
                fitsSystemWindows = true
            }
        } else {
            null
        }
    }

    override fun createView(): View {
        return getRootView().apply {
            // 设置背景颜色
            base.getRootBackgroundColor()?.let {
                val color = when (resources.getResourceTypeName(it)) {
                    "attr" -> {
                        QMUISkinHelper.getSkinColor(this, it)
                    }
                    "color" -> {
                        ContextCompat.getColor(context, it)
                    }
                    else -> {
                        throw RuntimeException("The getRootBackgroundColor() method must return an attr or color resource.")
                    }
                }
                setBackgroundColor(color)
            }
            val contentParams = ConstraintLayout.LayoutParams(matchParent, 0).apply {
                bottomToBottom = PARENT_ID
                endToEnd = PARENT_ID
                startToStart = PARENT_ID
                // 当显示TopBar且内容层不是充满时内容层才在TopBar下方
                if (base.showTopBar() && !base.translucentFull()) {
                    topToBottom = R.id.base_topbar
                } else {
                    topToTop = PARENT_ID
                }
            }
            addView(getContentView(), contentParams)
            if (base.showTopBar()) {
                // TopBar要放在后面（布局的上一层），如果body充满整个父容器时，要保证TopBar是在上面的。
                addView(getTopBar())
            }
        }
    }

}