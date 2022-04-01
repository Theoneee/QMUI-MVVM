package com.theone.mvvm.base

import android.content.Context
import android.view.View
import android.view.ViewGroup
import com.qmuiteam.qmui.kotlin.matchParent
import com.qmuiteam.qmui.kotlin.wrapContent
import com.qmuiteam.qmui.skin.QMUISkinHelper
import com.qmuiteam.qmui.util.QMUIResHelper
import com.qmuiteam.qmui.util.QMUIStatusBarHelper
import com.qmuiteam.qmui.widget.QMUITopBarLayout
import com.qmuiteam.qmui.widget.QMUIWindowInsetLayout
import com.theone.mvvm.R

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
    private val showTopBar: Boolean
) : ViewConstructor(context, contentFactory) {

    override fun createRootView(): ViewGroup = QMUIWindowInsetLayout(context)

    override fun createTopBar(): QMUITopBarLayout? {
        return if (showTopBar) {
            QMUITopBarLayout(context).apply {
                layoutParams = ViewGroup.LayoutParams(matchParent, wrapContent)
                fitsSystemWindows = true
            }
        } else {
            null
        }
    }

    override fun createView(translucentFull: Boolean): View {
        return getRootView().apply {
            // 设置内容层背景颜色
            setBackgroundColor(
                QMUISkinHelper.getSkinColor(
                    this,
                    R.attr.app_skin_main_background_color
                )
            )
            val contentParams = ViewGroup.MarginLayoutParams(matchParent, matchParent)
            if (showTopBar && !translucentFull) {
                val marginTop = QMUIResHelper.getAttrDimen(
                    context,
                    R.attr.qmui_topbar_height
                ) + QMUIStatusBarHelper.getStatusbarHeight(context)
                contentParams.topMargin = marginTop
            }
            addView(getContentView(),contentParams)
            if (showTopBar) {
                // TopBar要放在后面（布局的上一层），如果body充满整个父容器时，要保证TopBar是在上面的。
                addView(getTopBar())
            }
        }
    }

}