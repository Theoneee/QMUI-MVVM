package com.theone.mvvm.core.app.ext

import android.widget.ImageView
import android.widget.TextView
import com.theone.mvvm.core.app.widge.loadsir.core.LoadService
import com.theone.mvvm.core.app.widge.loadsir.core.LoadSir
import com.theone.mvvm.core.R
import com.theone.mvvm.core.base.callback.ICore
import com.theone.mvvm.core.app.widge.loadsir.callback.ErrorCallback
import com.theone.mvvm.core.app.widge.loadsir.callback.LoadingCallback
import com.theone.mvvm.core.app.widge.loadsir.callback.SuccessCallback


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
 * @date 2021/2/24 0024
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */


fun ICore.registerLoadSir(): LoadService<Any>? {
    loadSirRegisterView()?.let {
        return LoadSir.getDefault().register(it) {
            //点击重试时触发的操作
            onPageReLoad()
        }
    }
    return null
}

/**
 * 提供默认的init方法
 */
fun initLoadSir() {
    LoadSir.beginBuilder()
        .addCallback(LoadingCallback())
        .addCallback(ErrorCallback())
        .setDefaultCallback(SuccessCallback::class.java)
        .commit()
}

/**
 * 设置加载布局
 * @param message 加载布局显示的提示内容
 */
fun LoadService<*>.showLoading(
    message: String  = "加载中"
) {
    setCallBack(LoadingCallback::class.java) { _, view ->
        view.findViewById<TextView>(R.id.loading_tips).text = message
    }
    showCallback(LoadingCallback::class.java)
}

/**
 * 设置错误布局
 * @param message 错误布局显示的提示内容
 */
fun LoadService<*>.showError(
    message: String?,
    imageRes: Int = R.drawable.status_loading_view_loading_fail
) {
    setCallBack(ErrorCallback::class.java) { _, view ->
        view.findViewById<TextView>(R.id.stateContentTextView).text = message
        view.findViewById<ImageView>(R.id.stateImageView).setImageResource(imageRes)
    }
    showCallback(ErrorCallback::class.java)
}

private fun ICore.postShow(show:()->Unit){
    loadSirRegisterView()?.post {
        show.invoke()
    }
}

fun ICore.showSuccessPage() {
    postShow {
        getLoadSir()?.showSuccess()
    }
}

fun ICore.showLoadingPage(message: String = "加载中") {
    postShow {
        getLoadSir()?.showLoading(message)
    }
}

fun ICore.showErrorPage(
    message: String?,
    imageRes: Int = R.drawable.status_loading_view_loading_fail
) {
    postShow {
        getLoadSir()?.showError(message, imageRes)
    }
}

fun ICore.showEmptyPage(
    message: String = "暂无此内容",
    imageRes: Int = R.drawable.status_search_result_empty
) {
    showErrorPage(message, imageRes)
}
