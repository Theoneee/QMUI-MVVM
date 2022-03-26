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

abstract class Loader(private val layoutInflater: LayoutInflater) {

    private var loading: View? = null

    private var error: View? = null

    private var curStatus: LoaderStatus = LoaderStatus.SUCCESS

    private var loaderParams: ViewGroup.LayoutParams? = null

    private var rootView: ViewGroup? = null

    private var contentView:View?=null

    private fun getContentView() = contentView

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
        if (null != contentView) {
            throw RuntimeException("Loader已经注册,请勿重复注册...")
        }
        val parent = registerView?.parent
        rootView = when {
            parent is ViewGroup -> {
                contentView = registerView
                parent
            }
            registerView is ViewGroup -> {
                registerView
            }
            registerView is View ->{
                throw RuntimeException("Loader registerView 不能为 View")
            }
            else -> {
                throw RuntimeException("Loader registerView 不能为 null")
            }
        }
        loaderParams = registerView.layoutParams ?: match_match
        show(defaultStatus)
    }

    private fun ensureLoadingView() {
        if (null == loading) {
            rootView?.run {
                loading =
                    layoutInflater.inflate(getLoadingLayout(), this, false)
                addView(loading, loaderParams)
            }
        }
    }

    private fun ensureErrorView() {
        if (null == error) {
            rootView?.run {
                error =
                    layoutInflater.inflate(getErrorLayout(), this, false)
                addView(error, loaderParams)
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
                getContentView()?.visible()
                getLoadingView()?.invisible()
                getErrorView()?.invisible()
            }
            LoaderStatus.LOADING -> {
                ensureLoadingView()
                getLoadingView()?.visible()
                getContentView()?.gone()
                getErrorView()?.gone()
            }
            LoaderStatus.ERROR -> {
                ensureErrorView()
                getLoadingView()?.gone()
                getContentView()?.gone()
                getErrorView()?.visible()
            }
        }
    }

}