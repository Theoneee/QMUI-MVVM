package com.theone.mvvm.core.base.loader

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.theone.common.ext.*
import com.theone.mvvm.core.base.loader.callback.Callback
import com.theone.mvvm.core.base.loader.callback.SuccessCallback
import java.lang.RuntimeException

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

class LoaderView {

    private val callbackMap = mutableMapOf<Class<out Callback>, Callback>()

    private var callbacks: List<Class<out Callback>>? = null

    private var curCallback: Class<out Callback> = SuccessCallback::class.java

    private var loaderParams: ViewGroup.LayoutParams? = null

    private var rootView: ViewGroup? = null

    private var contentView: View? = null

    private fun getContentView() = contentView

    fun getCurrentCallback() = curCallback

    fun showSuccessPage() = show(SuccessCallback::class.java)

    fun register(target: View?, builder: Loader.Builder?): LoaderView {
        if (null == target) {
            throw RuntimeException("Loader 不能注册一个空的View")
        }
        if (null != loaderParams) {
            throw RuntimeException("Loader已经注册,请勿重复注册...")
        }
        val parent = target.parent
        rootView = when {
            parent is ViewGroup -> {
                contentView = target
                parent
            }
            target is ViewGroup -> {
                target
            }
            else -> {
                throw RuntimeException("Loader registerView 不能为 View")
            }
        }
        loaderParams = target.layoutParams ?: match_match
        builder?.run {
            callbacks = getCallbacks()
            show(getDefaultCallback())
        }
        return this
    }

    private fun Callback.ensureCallback() {
        if (null == view) {
            rootView?.run {
                view =
                    LayoutInflater.from(context)
                        .inflate(this@ensureCallback.layoutId(), this, false)
                addView(view, loaderParams)
            }
        }
    }

    fun show(
        callback: Class<out Callback>?,
        transport: ((context: Context, view: View?) -> Unit)? = null
    ) {
        if (callback == null || callback == curCallback) {
            return
        }
        callbacks?.let { callbacks ->
            // 设置内容层
            getContentView()?.visible(callback == SuccessCallback::class.java)
            // 遍历循环，如果设置的是内容层，那么一定会走 TODO[2]
            for (item in callbacks) {
                val instance = if (callbackMap.containsKey(item)) {
                    callbackMap[item]
                } else {
                    val instance = item.newInstance()
                    callbackMap[item] = instance
                    instance
                }
                instance?.run {
                    if (javaClass == callback) {
                        // TODO[1] 需要显示的时候才加载到[rootView]
                        ensureCallback()
                        view?.let {
                            it.visible()
                            transport?.invoke(it.context, it)
                        }
                    } else {
                        // TODO[2] 将不是当前设置的状态全部
                        view?.gone()
                    }
                }
            }
        }
        curCallback = callback
    }

}