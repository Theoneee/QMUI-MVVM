package com.theone.mvvm.core.app.widge

import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.webkit.WebSettings
import com.qmuiteam.qmui.util.QMUIResHelper
import com.qmuiteam.qmui.widget.webview.QMUIWebView
import com.theone.mvvm.core.R

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
 * @date 2021-04-26 17:18
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
class TheWebView(context: Context, atts: AttributeSet? = null) :
    QMUIWebView(context, atts, android.R.attr.webViewStyle) {

    init {
        settings.run {
            javaScriptEnabled = true
            setSupportZoom(true)
            builtInZoomControls = true
            defaultTextEncodingName = "GBK"
            useWideViewPort = true
            loadWithOverviewMode = true
            domStorageEnabled = true
            cacheMode = WebSettings.LOAD_NO_CACHE
            textZoom = 100

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                mixedContentMode = WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE
            }

//            val screen = "${QMUIDisplayHelper.getScreenWidth(context)}x${QMUIDisplayHelper.getScreenHeight(context)}"

        }

    }

    override fun getExtraInsetTop(density: Float): Int {
        return (QMUIResHelper.getAttrDimen(
            context,
            R.attr.qmui_topbar_height
        ) / density).toInt()
    }

}