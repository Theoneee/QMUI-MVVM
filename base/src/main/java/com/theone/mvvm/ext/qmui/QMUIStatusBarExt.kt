package com.theone.mvvm.ext.qmui

import android.app.Activity
import androidx.fragment.app.Fragment
import com.qmuiteam.qmui.util.QMUIStatusBarHelper

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
 * @date 2021-03-29 14:12
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */

/**
 * 更新状态栏模式
 *
 * @param isLight true 设置状态栏黑色字体图标，
 *
 * 支持 4.4 以上版本 MIUI 和 Flyme，以及 6.0 以上版本的其他 Android
 */
fun Activity.updateStatusBarMode(isLight: Boolean) {
    if (isLight) {
        QMUIStatusBarHelper.setStatusBarLightMode(this)
    } else {
        QMUIStatusBarHelper.setStatusBarDarkMode(this)
    }
}

fun Fragment.updateStatusBarMode(isLight: Boolean) {
    requireActivity().updateStatusBarMode(isLight)
}
