package com.theone.mvvm.base.viewmodel

import androidx.lifecycle.ViewModel
import com.kunminx.architecture.ui.callback.ProtectedUnPeekLiveData
import com.kunminx.architecture.ui.callback.UnPeekLiveData
import com.theone.mvvm.entity.ProgressBean


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
 * @date 2021/2/22 0022
 * @describe ViewModel基类
 * @email 625805189@qq.com
 * @remark
 */
open class BaseViewModel : ViewModel() {

    protected val TAG: String = this.javaClass.simpleName

    private val uiChange: UiChange by lazy { UiChange() }

    inner class UiChange {
        //显示加载框
        val showLoading by lazy { UnPeekLiveData<String>() }

        //隐藏
        val hideLoading by lazy { UnPeekLiveData<Boolean>() }

        //显示进度弹窗
        val showProgress by lazy { UnPeekLiveData<ProgressBean>() }

        // 隐藏进度弹窗
        val hideProgress by lazy { UnPeekLiveData<Boolean>() }
    }

    // 供子类和外部调用

    fun getShowLoadingLiveData(): ProtectedUnPeekLiveData<String> = uiChange.showLoading
    fun getHideLoadingLiveData():ProtectedUnPeekLiveData<Boolean> = uiChange.hideLoading
    fun getShowProgressLiveData():ProtectedUnPeekLiveData<ProgressBean> = uiChange.showProgress
    fun getHideProgressLiveData():ProtectedUnPeekLiveData<Boolean> = uiChange.hideProgress

    /**
     * 显示加载弹窗
     * @param msg String?
     */
    open fun showLoadingDialog(msg: String?) {
        uiChange.showLoading.value = msg
    }

    /**
     * 隐藏加载弹窗
     */
    open fun hideLoadingDialog() {
        uiChange.hideLoading.value = true
    }

    /**
     * 显示进度弹窗
     * @param msg String? 提示语
     * @param percent Int 当前进度
     * @param max Int 总进度
     * @param outSideCancel Boolean 点击外部是否能关闭
     * @param keyBackCancel Boolean 点击返回键是否能关闭
     */
    open fun showProgressDialog(msg: String? = "", percent: Int = 0, max: Int = 100, outSideCancel:Boolean = false, keyBackCancel:Boolean = false) {
        uiChange.showProgress.value = ProgressBean(msg, percent, max,outSideCancel,keyBackCancel)
    }

    /**
     * 隐藏进度弹窗
     */
    open fun hideProgressDialog() {
        uiChange.hideProgress.value = true
    }

}