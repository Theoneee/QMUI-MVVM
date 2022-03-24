package com.theone.mvvm.core.app.ext

import android.annotation.SuppressLint
import android.app.Activity
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
import com.theone.mvvm.ext.qmui.hideLoadingDialog
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

fun Activity.showProgressDialog(data:ProgressBean){
    with(data){
        if(null == progressDialog){
            progressDialog = ProgressDialog(this@showProgressDialog).apply {
                setCanceledOnTouchOutside(outSideCancel)
                setOnKeyListener(OnKeyBackClickListener(!keyBackCancel))
            }
        }
        progressDialog?.run {
            setMessage(msg)
            setProgress(percent,max)
            if(!isShowing){
                show()
            }
        }
    }

}

fun hideProgressDialog(){
    progressDialog?.dismiss()
    progressDialog = null
}

fun Fragment.addFailTipsObserve(vararg vms: BaseRequestViewModel<*>) {
    for (vm in vms) {
        vm.getErrorLiveData().observe(this, Observer {
            hideLoadingDialog()
            hideProgressDialog()
            showFailTipsDialog(it)
        })
    }
}


fun Activity.startAppUpdateActivity(update: IApkUpdate, clazz:Class<*> = AppUpdateActivity::class.java) {
    startActivity(Intent(this, clazz).apply {
        putExtra(BundleConstant.DATA, update)
    })
}