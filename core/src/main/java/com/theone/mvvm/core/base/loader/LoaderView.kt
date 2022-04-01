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
 * @date 2022-03-25 09:51
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */

class LoaderView {

    private val TAG = this.javaClass.simpleName

    private val callbacks = mutableListOf<Callback>()

    private var preCallback: Class<out Callback>? = null

    private var curCallback: Class<out Callback> = SuccessCallback::class.java

    private var rootView: ViewGroup? = null

    private var successCallback: SuccessCallback? = null

    private var loaderParams: ViewGroup.LayoutParams? = null

    private var loaderIndex = 1

    fun getCurrentCallback() = curCallback

    fun showSuccessPage() = showCallbackView(SuccessCallback::class.java)

    fun register(target: View, builder: Loader.Builder?): LoaderView {
        if (null != rootView) {
            throw RuntimeException("Loader has been registered.")
        }
        val parent = target.parent
        rootView = when {
            parent is ViewGroup -> {
                successCallback = SuccessCallback().apply {
                    view = target
                }
                loaderIndex = parent.childCount
                preView = target
                parent
            }
            target is ViewGroup -> {
                target
            }
            else -> {
                throw RuntimeException("Loader target must have a parent")
            }
        }
        loaderParams = target.layoutParams ?: ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        successCallback?.let {
            callbacks.add(it)
        }
        builder?.run {
            for (callback in getCallbacks()) {
                callbacks.add(callback.newInstance())
            }
            showCallbackView(getDefaultCallback())
        }
        return this
    }

    private fun ensureRootView(): ViewGroup {
        if (null == rootView) {
            throw IllegalArgumentException("Loader must have a rootView")
        }
        return rootView as ViewGroup
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
        transport: ((context: Context, view: View?) -> Unit)? = null
    ) {
        for (item in callbacks) {
            if (item.javaClass == status) {
                preCallback = status
                if (status == SuccessCallback::class.java) {
                    if (null == successCallback) {
                        preView?.let {
                            ensureRootView().removeViewInLayout(it)
                        }
                    } else {
                        successCallback?.view?.let {
                            replaceContentWithView(it)
                        }
                    }
                } else {
                    with(item) {
                        ensureView(ensureRootView()).let {
                            replaceContentWithView(it)
                            transport?.invoke(it.context, it)
                        }
                    }
                }
                break
            }
        }
        curCallback = status
    }

    private var preView: View? = null

    private fun replaceContentWithView(view: View) {
        with(ensureRootView()) {
            if (successCallback == null) {
                preView?.let {
                    removeViewInLayout(it)
                }
                addView(view, loaderParams)
            } else {
                preView?.let {
                    // 设置id,解决ConstraintLayout布局问题
                    val id = it.id
                    if (id > 0) {
                        view.id = id
                    }
                    val index = indexOfChild(it)
                    removeViewInLayout(it)
                    addView(view, index, loaderParams)
                    // 某些机型addView后不执行此方法，这里手动执行下
                    requestApplyInsets()
                }
            }
            preView = view
        }
    }

}