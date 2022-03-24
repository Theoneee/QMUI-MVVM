package com.theone.mvvm.core.app.ext

import android.view.View
import android.webkit.WebView
import android.widget.ZoomButtonsController
import com.theone.common.constant.BundleConstant
import com.theone.common.ext.bundle
import com.theone.mvvm.base.fragment.BaseQMUIFragment
import com.theone.mvvm.core.base.callback.IWeb
import com.theone.mvvm.core.ui.fragment.SampleWebFragment
import java.lang.reflect.Field

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
 * @date 2021-04-27 14:53
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */

fun WebView.addImageListenerJs(method: String) {
    loadUrl("javascript:(function(){" +
            "var objs = document.getElementsByTagName(\"img\"); " +
            " var array=new Array(); " +
            " for(var j=0;j<objs.length;j++){ array[j]=objs[j].src; }" +
            "for(var i=0;i<objs.length;i++)  " +
            "{"+
             "objs[i].onclick=function()" +
            "{"+
             " window.WebViewJavascriptBridge.callHandler('$method',{'position':this.src,'data':array});"+
            "}" +
            "}" +
            "})()")
}

fun WebView.setZoomControlGone() {
    settings.displayZoomControls = false
    val classType: Class<*>
    val field: Field
    try {
        classType = WebView::class.java
        field = classType.getDeclaredField("mZoomButtonsController")
        field.isAccessible = true
        val zoomButtonsController = ZoomButtonsController(
            this
        )
        zoomButtonsController.zoomControls.visibility = View.GONE
        try {
            field[this] = zoomButtonsController
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        }
    } catch (e: SecurityException) {
        e.printStackTrace()
    } catch (e: NoSuchFieldException) {
        e.printStackTrace()
    }
}

fun <T : IWeb> BaseQMUIFragment.startWebFragment(data: T) {
    startFragment(SampleWebFragment().bundle {
        putParcelable(BundleConstant.DATA, data)
    })
}
