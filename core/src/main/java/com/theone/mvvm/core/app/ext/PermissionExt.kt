package com.theone.mvvm.core.app.ext

import android.content.Context
import com.hjq.permissions.Permission
import com.hjq.permissions.XXPermissions
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction
import com.theone.common.callback.OnKeyBackClickListener
import com.theone.mvvm.ext.qmui.showFailTipsDialog
import com.theone.mvvm.ext.qmui.showMsgDialog
import java.lang.StringBuilder

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
 * @date 2022-07-06 16:15
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
fun Context.showNoPermissionDialog(quick: Boolean, denied: MutableList<String>?) {
    val permissions = denied?.getPermissionName()
    if (quick) {
        showMsgDialog(
            "提示",
            "${permissions}权限被拒绝，请手动打开",
            rightAction = "前往设置",
            listener = { dialog, which ->
                dialog.dismiss()
                if (which > 0) {
                    XXPermissions.startPermissionActivity(this, denied)
                }
            },
            prop = QMUIDialogAction.ACTION_PROP_NEGATIVE
        ).run {
            setCanceledOnTouchOutside(false)
            setOnKeyListener(OnKeyBackClickListener())
        }
    } else {
        showFailTipsDialog("${permissions}权限获取失败")
    }
}

fun MutableList<String>.getPermissionName(): String {
    val sb = StringBuilder()
    for (item in this) {
        val pName = when (item) {
            Permission.CAMERA -> "相机"
            Permission.RECORD_AUDIO -> "录音"
            Permission.READ_EXTERNAL_STORAGE -> "存储"
            Permission.ACCESS_COARSE_LOCATION -> "定位"
            else -> ""
        }
        if (pName.isNotEmpty()) {
            sb.append(pName)
            sb.append("、")
        }
    }
    return sb.toString()
}