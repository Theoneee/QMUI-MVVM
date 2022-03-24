package com.theone.mvvm.ext.qmui

import android.content.Context
import androidx.fragment.app.Fragment
import com.qmuiteam.qmui.arch.QMUIFragment
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog
import com.theone.mvvm.base.fragment.BaseQMUIFragment
import com.theone.mvvm.base.viewmodel.BaseViewModel

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
 * @date 2021-03-30 13:15
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */

fun Fragment.showMsgTipsDialog(msg: CharSequence?, delay: Long = 1000, callback: (() -> Any?)? = null) {
    context?.showTipsDelayedDismiss(msg, QMUITipDialog.Builder.ICON_TYPE_NOTHING, delay, callback)
}

fun Fragment.showInfoTipsDialog(msg: CharSequence?, delay: Long = 1000, callback: (() -> Any?)? = null) {
    context?.showTipsDelayedDismiss(msg, QMUITipDialog.Builder.ICON_TYPE_INFO, delay, callback)
}

fun BaseQMUIFragment.showSuccessTipsExitDialog(msg: CharSequence?) {
    context?.showSuccessTipsDialog(msg) {
        finish()
    }
}

fun Fragment.showSuccessTipsDialog(msg: CharSequence?, delay: Long = 1000, callback: (() -> Any?)? = null) {
    context?.showTipsDelayedDismiss(msg, QMUITipDialog.Builder.ICON_TYPE_SUCCESS, delay, callback)
}

fun Fragment.showFailTipsDialog(msg: CharSequence?, delay: Long = 1000, callback: (() -> Any?)? = null) {
    context?.showTipsDelayedDismiss(msg, QMUITipDialog.Builder.ICON_TYPE_FAIL, delay, callback)
}
