package com.theone.common.ext

import android.content.Context
import androidx.customview.widget.ViewDragHelper
import androidx.drawerlayout.widget.DrawerLayout

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
 * @date 2022-03-07 09:33
 * @describe 一些组件的扩展方法
 * @email 625805189@qq.com
 * @remark
 */


/**
 * DrawerLayout全屏滑动
 * @param context Context
 * @param drawerLayout DrawerLayout
 * @param dragPercent Float
 */
fun setDrawerLeftEdgeSize(
    context: Context,
    drawerLayout: DrawerLayout,
    dragPercent: Float
) {
    try {
        val leftDrawerField = drawerLayout.javaClass.getDeclaredField("mLeftDragger").apply {
            isAccessible = true
        }
        val helper = leftDrawerField.get(drawerLayout) as ViewDragHelper
        val edgeSizeField = helper.javaClass.getDeclaredField("mEdgeSize").apply {
            isAccessible = true
        }
        val edgeSize = edgeSizeField.getInt(helper)
        val width = context.screenWidth
        edgeSizeField.setInt(
            helper, edgeSize.coerceAtLeast(
                (width * dragPercent).toInt()
            )
        )
    } catch (e: Exception) {
        e.printStackTrace()
    }
}
