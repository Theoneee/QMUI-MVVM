package com.theone.mvvm.core.base.loader

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.theone.common.ext.*
import com.theone.mvvm.base.ViewConstructor
import java.lang.RuntimeException
import kotlin.jvm.Throws

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
 * @date 2022-03-25 09:51
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */

enum class LoaderStatus {
    SUCCESS, LOADING, ERROR
}

abstract class BaseLoaderView(val constructor: ViewConstructor) {

    private var loading: View? = null

    private var error: View? = null

    private var curStatus: LoaderStatus = LoaderStatus.SUCCESS

    private var loaderParams: ViewGroup.LayoutParams? = null

    private fun getContentView() = constructor.getContentView()

    abstract fun getLoadingLayout(): Int

    abstract fun getErrorLayout(): Int

    open fun getCurrentStatus() = curStatus

    open fun getLoadingView() = loading

    open fun getErrorView() = error

    fun showSuccessPage() = show(LoaderStatus.SUCCESS)

    fun register(
        registerView: View?,
        defaultStatus: LoaderStatus = LoaderStatus.SUCCESS
    ) {
        if (null != loaderParams) {
            throw RuntimeException("Loader已经注册,请勿重复注册...")
        }
        loaderParams = registerView?.layoutParams ?: match_match
        show(defaultStatus)
    }

    private fun ensureLoadingView() {
        if (null == loading) {
            with(constructor) {
                loading = getLayoutInflater().inflate(getLoadingLayout(), getRootView(), false)
                getRootView().addView(loading, loaderParams)
            }
        }
    }

    private fun ensureErrorView() {
        if (null == error) {
            with(constructor) {
                error = getLayoutInflater().inflate(getErrorLayout(), getRootView(), false)
                getRootView().addView(error, loaderParams)
            }
        }
    }

    fun show(status: LoaderStatus) {
        if (status == curStatus) {
            return
        }
        curStatus = status
        when (status) {
            LoaderStatus.SUCCESS -> {
                getContentView().visible()
                getLoadingView()?.invisible()
                getErrorView()?.invisible()
            }
            LoaderStatus.LOADING -> {
                ensureLoadingView()
                getLoadingView()?.visible()
                getContentView().gone()
                getErrorView()?.gone()
            }
            LoaderStatus.ERROR -> {
                ensureErrorView()
                getLoadingView()?.gone()
                getContentView().gone()
                getErrorView()?.visible()
            }
        }
    }

}