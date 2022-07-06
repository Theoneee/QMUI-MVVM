package com.theone.mvvm.core.app.ext

import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.theone.loader.Loader
import com.theone.loader.LoaderService
import com.theone.mvvm.core.R
import com.theone.mvvm.core.base.callback.ICore
import com.theone.mvvm.core.base.loader.callback.ErrorCallback
import com.theone.mvvm.core.base.loader.callback.LoadingCallback

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
 * @date 2022-03-25 11:28
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */

fun initLoaderDefault() {
    Loader.beginBuilder()
        .addCallback(LoadingCallback::class.java)
        .addCallback(ErrorCallback::class.java)
        .commit()
}

fun ICore.registerLoader(): LoaderService? {
    return loaderRegisterView()?.let { registerView ->
        Loader.getDefault().register(registerView, loaderDefaultCallback())
    }
}

fun ICore.showSuccessPage() {
    getLoader()?.showSuccessPage()
}

fun ICore.showLoadingPage(msg: String? = null) {
    getLoader()?.showLoadingPage(msg)
}

fun LoaderService.showLoadingPage(msg: String? = null) {
    showCallbackView(LoadingCallback::class.java) { _, view ->
        msg?.let {
            view?.findViewById<TextView>(R.id.loading_tips)?.text = it
        }
    }
}

fun ICore.showEmptyPage(
    msg: String = "暂无此内容",
    retryMsg: String = "刷新看看",
    imageRes: Int = R.drawable.status_search_result_empty,
    click: ((View) -> Unit)? = null
) {
    getLoader()?.showErrorPage(msg, retryMsg, imageRes, click)
}

fun ICore.showErrorPage(
    msg: String?,
    retryMsg: String = "点击重试",
    imageRes: Int = R.drawable.status_loading_view_loading_fail,
    click: ((View) -> Unit)? = null
) {
    getLoader()?.showErrorPage(msg, retryMsg, imageRes, click)
}

fun LoaderService.showErrorPage(
    msg: String?,
    retryMsg: String = "点击重试",
    imageRes: Int = R.drawable.status_loading_view_no_network,
    click: ((View) -> Unit)? = null
) {
    showCallbackView(ErrorCallback::class.java) { _, view ->
        view?.run {
            msg?.let {
                findViewById<TextView>(R.id.stateContentTextView).text = it
            }
            findViewById<ImageView>(R.id.stateImageView).setImageResource(
                when (msg) {
                    NET_ERROR -> R.drawable.status_loading_view_no_network
                    NET_TIME_OUT -> R.drawable.status_loading_view_no_network
                    NET_CONNECT_ERROR -> R.drawable.status_loading_view_network_error
                    else -> imageRes
                }
            )
            findViewById<Button>(R.id.retry).run {
                visibility = if (null == click) View.GONE else View.VISIBLE
                click?.let {
                    text = retryMsg
                    setOnClickListener(it)
                }
            }
        }
    }

}