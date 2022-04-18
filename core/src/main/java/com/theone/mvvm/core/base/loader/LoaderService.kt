package com.theone.mvvm.core.base.loader

import android.content.Context
import android.os.Looper
import android.view.View
import android.view.ViewGroup
import com.theone.mvvm.core.base.loader.callback.Callback
import com.theone.mvvm.core.base.loader.callback.SuccessCallback

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
 * @date 2022-04-02 14:54
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
abstract class LoaderService {

    private val TAG = this.javaClass.simpleName

    val callbacks = mutableListOf<Callback>()

    var preCallback: Class<out Callback>? = null

    var curCallback: Class<out Callback> = SuccessCallback::class.java

    var rootView: ViewGroup? = null

    var preView: View? = null

    var successCallback: SuccessCallback? = null

    var loaderParams: ViewGroup.LayoutParams? = null

    var loaderId: Int = 0

    fun getCurrentCallback() = curCallback

    fun showSuccessPage() = showCallbackView(SuccessCallback::class.java)

    fun ensureRootView(): ViewGroup {
        if (null == rootView) {
            throw IllegalArgumentException("Loader must have a rootView")
        }
        return rootView as ViewGroup
    }

    fun register(target: View, builder: Loader.Builder?,default:Class<out Callback>?): LoaderService {
        if (null != rootView) {
            throw RuntimeException("Loader has been registered.")
        }
        val parent = target.parent
        rootView = when (parent) {
            is ViewGroup -> {
                successCallback = SuccessCallback().apply {
                    view = target
                    callbacks.add(this)
                }
                preView = target
                parent
            }
            else -> {
                throw RuntimeException("Loader target must have a parent")
            }
        }
        loaderId= target.id
        loaderParams = target.layoutParams
        builder?.run {
            for (callback in getCallbacks()) {
                callbacks.add(callback.newInstance())
            }
            showCallbackView(default?:getDefaultCallback())
        }
        return this
    }

    fun showCallbackView(
        status: Class<out Callback>?,
        transport: ((context: Context, view: View?) -> Unit)? = null
    ) {
        if (null == status) {
            return
        }
        if (preCallback != null) {
            if (preCallback == status) {
                return
            }
        }
        if (status == curCallback) {
            return
        }
        if (Looper.myLooper() == Looper.getMainLooper()) {
            show(status, transport)
        } else {
            ensureRootView().post {
                show(status, transport)
            }
        }
    }

    private fun show(
        status: Class<out Callback>,
        transport: ((context: Context, view: View?) -> Unit)?
    ) {
        for (item in callbacks) {
            if (item.javaClass == status) {
                preCallback = status
                if (status == SuccessCallback::class.java) {
                    successCallback?.view?.let {
                        showSuccessView(it)
                    }
                } else {
                    with(item) {
                        ensureView(ensureRootView()).let {
                            transport?.invoke(it.context, it)
                            showLoaderView(it)
                        }
                    }
                }
                break
            }
        }
        curCallback = status
    }

    abstract fun showSuccessView(view: View)

    abstract fun showLoaderView(view: View)

}