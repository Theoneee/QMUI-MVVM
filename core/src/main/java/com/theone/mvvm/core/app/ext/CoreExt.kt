package com.theone.mvvm.core.app.ext

import android.annotation.SuppressLint
import android.app.Activity
import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.theone.common.callback.OnKeyBackClickListener
import com.theone.common.constant.BundleConstant
import com.theone.mvvm.core.base.viewmodel.BaseRequestViewModel
import com.theone.mvvm.core.app.widge.dialog.ProgressDialog
import com.theone.mvvm.core.base.callback.IApkUpdate
import com.theone.mvvm.core.ui.activity.AppUpdateActivity
import com.theone.mvvm.entity.ProgressBean
import com.theone.mvvm.ext.qmui.hideLoadingDialogExt
import com.theone.mvvm.ext.qmui.showFailTipsDialog

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
 * @date 2021-04-30 16:49
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */

@SuppressLint("StaticFieldLeak")
private var progressDialog: ProgressDialog? = null

fun Activity.showProgressDialog(data: ProgressBean) {
    with(data) {
        if (null == progressDialog) {
            progressDialog = ProgressDialog(this@showProgressDialog).apply {
                setCanceledOnTouchOutside(outSideCancel)
                setOnKeyListener(OnKeyBackClickListener(!keyBackCancel))
            }
        }
        progressDialog?.run {
            setMessage(msg)
            setProgress(percent, max)
            if (!isShowing) {
                show()
            }
        }
    }

}

fun hideProgressDialogExt() {
    progressDialog?.dismiss()
    progressDialog = null
}

fun Fragment.addFailTipsObserve(vararg vms: BaseRequestViewModel<*>) {
    for (vm in vms) {
        vm.getErrorLiveData().observe(this, Observer {
            hideLoadingDialogExt()
            hideProgressDialogExt()
            showFailTipsDialog(it)
        })
    }
}

fun Activity.startAppUpdateActivity(
    update: IApkUpdate,
    clazz: Class<*> = AppUpdateActivity::class.java
) {
    startActivity(Intent(this, clazz).apply {
        putExtra(BundleConstant.DATA, update)
    })
}

/**
 * 服务是否运行
 * @param targetName String
 * @return Boolean
 */
fun Context.isServiceRunning(targetName: String?): Boolean {
    val am = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
    val serviceList = am.getRunningServices(200)
    if (serviceList.size <= 0) {
        return false
    }
    for (running in serviceList) {
        val serviceName = running.service.className
        if (serviceName == targetName) {
            return true
        }
    }
    return false
}
