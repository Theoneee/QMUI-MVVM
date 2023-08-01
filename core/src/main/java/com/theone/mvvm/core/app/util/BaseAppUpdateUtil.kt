package com.theone.mvvm.core.app.util

import androidx.appcompat.app.AppCompatActivity
import com.theone.common.ext.isServiceExisted
import com.theone.mvvm.core.app.ext.startAppUpdateActivity
import com.theone.mvvm.core.base.callback.IApkUpdate
import com.theone.mvvm.ext.qmui.hideLoadingDialogExt
import com.theone.mvvm.ext.qmui.showFailTipsDialog
import com.theone.mvvm.ext.qmui.showLoadingDialogExt
import com.theone.mvvm.ext.qmui.showSuccessTipsDialog

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
 * @date 2021-05-08 11:38
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
abstract class BaseAppUpdateUtil<T: IApkUpdate>(val context:AppCompatActivity, val showCheck:Boolean) {

    protected open fun getDownloadServiceName():String = ""

    abstract fun requestServe()

    fun checkUpdate(){
        if(isServiceExisted(context,getDownloadServiceName())){
            context.showFailTipsDialog("更新包正在下载中")
            return
        }else if (showCheck) {
            showCheckDialog()
        }
        requestServe()
    }

    protected open fun T.onComplete(){
        hideCheckDialog()
        if(isNewVersion()){
            showNewVersionDialog(this)
        }else if(showCheck){
            showIsNewVersionDialog()
        }
    }

    protected open fun onError(errorMsg:String){
        hideCheckDialog()
        context.showFailTipsDialog(errorMsg)
    }

    protected open fun showCheckDialog(){
        context.showLoadingDialogExt("检查中")
    }

    protected open fun hideCheckDialog(){
        hideLoadingDialogExt()
    }

    protected open fun showIsNewVersionDialog(){
        context.showSuccessTipsDialog("已是最新版本")
    }

    protected open fun showNewVersionDialog(update:T){
        context.startAppUpdateActivity(update)
    }

}