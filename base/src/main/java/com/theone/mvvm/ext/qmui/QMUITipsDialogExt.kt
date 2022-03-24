package com.theone.mvvm.ext.qmui

import android.content.Context
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog


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
 * @date 2021/3/5 0005
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */

private var loadingDialog: QMUITipDialog? = null

fun Context.showLoadingDialog(msg: CharSequence?) {
    if(null != loadingDialog){
       hideLoadingDialog()
    }
    loadingDialog = createQMUIDialog(msg, QMUITipDialog.Builder.ICON_TYPE_LOADING)
}

fun hideLoadingDialog() {
    loadingDialog?.dismiss()
    loadingDialog = null
}

fun Context.showMsgTipsDialog(msg: CharSequence?, delay: Long = 1000, callback: (() -> Any?)? = null) {
    showTipsDelayedDismiss(msg, QMUITipDialog.Builder.ICON_TYPE_NOTHING, delay, callback)
}

fun Context.showInfoTipsDialog(msg: CharSequence?, delay: Long = 1000, callback: (() -> Any?)? = null) {
    showTipsDelayedDismiss(msg, QMUITipDialog.Builder.ICON_TYPE_INFO, delay, callback)
}

fun Context.showSuccessTipsDialog(msg: CharSequence?, delay: Long = 1000, callback: (() -> Any?)? = null) {
    showTipsDelayedDismiss(msg, QMUITipDialog.Builder.ICON_TYPE_SUCCESS, delay, callback)
}

fun Context.showFailTipsDialog(msg: CharSequence?, delay: Long = 1000, callback: (() -> Any?)? = null) {
    showTipsDelayedDismiss(msg, QMUITipDialog.Builder.ICON_TYPE_FAIL, delay, callback)
}

fun Context.showTipsDelayedDismiss(
    msg: CharSequence?,
    type: Int,
    delay: Long = 1000,
    callback: (() -> Any?)? = null
) {
    val dialog = createQMUIDialog(msg, type)
    android.os.Handler().postDelayed({
        dialog.dismiss()
        callback?.invoke()
    }, delay)
}

fun Context.createQMUIDialog(msg: CharSequence?, type: Int): QMUITipDialog {
    return QMUITipDialog.Builder(this)
        .setIconType(type)
        .setTipWord(msg)
        .create().apply {
            setCancelable(false)
            setCanceledOnTouchOutside(false)
            show()
        }
}