package com.theone.mvvm.core.app.ext

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.theone.mvvm.core.R
import com.theone.mvvm.core.base.callback.ICore
import com.theone.mvvm.core.base.loader.Loader
import com.theone.mvvm.core.base.loader.LoaderView
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

fun ICore.registerLoader():LoaderView? {
    return loaderRegisterView()?.let { registerView ->
        with(Loader.getDefault()){
            loaderDefaultCallback()?.let {
                builder?.defaultCallback(it)
            }
            register(registerView)
        }
    }
}

fun ICore.showSuccessPage() {
    getLoaderView()?.showSuccessPage()
}

fun ICore.showLoadingPage(msg: String? = null) {
    getLoaderView()?.showLoadingPage(msg)
}

fun ICore.showEmptyPage(
    msg: String = "暂无此内容",
    imageRes: Int = R.drawable.status_search_result_empty,
    click: ((View) -> Unit)? = null
) {
    getLoaderView()?.showErrorPage(msg, imageRes, click)
}

fun ICore.showErrorPage(
    msg: String?,
    imageRes: Int = R.drawable.status_loading_view_loading_fail,
    click: ((View) -> Unit)? = null
) {
    getLoaderView()?.showErrorPage(msg, imageRes, click)
}

fun LoaderView.showLoadingPage(msg: String? = null) {
    show(LoadingCallback::class.java){_,view ->
        msg?.let {
            view?.findViewById<TextView>(R.id.loading_tips)?.text = it
        }
    }
}

fun LoaderView.showErrorPage(
    msg: String?,
    imageRes: Int = R.drawable.status_loading_view_loading_fail,
    click: ((View) -> Unit)? = null
) {
    show(ErrorCallback::class.java){_, view ->
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