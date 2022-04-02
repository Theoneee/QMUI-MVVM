package com.theone.mvvm.core.app.ext

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.theone.mvvm.core.R
import com.theone.mvvm.core.base.callback.ICore
import com.theone.mvvm.core.base.loader.Loader
import com.theone.mvvm.core.base.loader.LoaderService
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

fun ICore.registerLoader(): LoaderService? {
    return loaderRegisterView()?.let { registerView ->
        Loader.getDefault().register(loaderServiceClass(),registerView,loaderDefaultCallback())
    }
}

fun ICore.showSuccessPage() {
    getLoader()?.showSuccessPage()
}

fun ICore.showLoadingPage(msg: String? = null) {
    getLoader()?.showLoadingPage(msg)
}

fun ICore.showEmptyPage(
    msg: String = "暂无此内容",
    imageRes: Int = R.drawable.status_search_result_empty,
    click: ((View) -> Unit)? = null
) {
    getLoader()?.showErrorPage(msg, imageRes, click)
}

fun ICore.showErrorPage(
    msg: String?,
    imageRes: Int = R.drawable.status_loading_view_loading_fail,
    click: ((View) -> Unit)? = null
) {
    getLoader()?.showErrorPage(msg, imageRes, click)
}

fun LoaderService.showLoadingPage(msg: String? = null) {
    showCallbackView(LoadingCallback::class.java){_,view ->
        msg?.let {
            view?.findViewById<TextView>(R.id.loading_tips)?.text = it
        }
    }
}

fun LoaderService.showErrorPage(
    msg: String?,
    imageRes: Int = R.drawable.status_loading_view_loading_fail,
    click: ((View) -> Unit)? = null
) {
    showCallbackView(ErrorCallback::class.java){_, view ->
        view?.run {
            msg?.let {
                findViewById<TextView>(R.id.stateContentTextView).text = it
            }
            val ivStatus = findViewById<ImageView>(R.id.stateImageView)
            ivStatus.setImageResource(imageRes)
            click?.let {
                setOnClickListener(it)
            }
        }
    }

}