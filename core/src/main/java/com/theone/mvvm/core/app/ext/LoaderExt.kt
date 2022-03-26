package com.theone.mvvm.core.app.ext

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.theone.mvvm.core.R
import com.theone.mvvm.core.base.callback.ICore
import com.theone.mvvm.core.base.loader.LoaderStatus
import com.theone.mvvm.core.base.loader.Loader

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

fun ICore.registerLoader(){
    getLoader()?.register(loaderRegisterView(),loaderDefaultStatus())
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

fun Loader.showLoadingPage(msg: String? = null) {
    show(LoaderStatus.LOADING)
    msg?.let {
        getLoadingView()?.findViewById<TextView>(R.id.loading_tips)?.text = it
    }
}

fun Loader.showErrorPage(
    msg: String?,
    imageRes: Int = R.drawable.status_loading_view_loading_fail,
    click: ((View) -> Unit)? = null
) {
    show(LoaderStatus.ERROR)
    getErrorView()?.run {
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