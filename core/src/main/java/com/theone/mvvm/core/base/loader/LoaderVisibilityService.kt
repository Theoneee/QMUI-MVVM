package com.theone.mvvm.core.base.loader

import android.view.View
import android.view.View.NO_ID
import com.theone.common.ext.gone
import com.theone.common.ext.visible


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
 * @describe LoaderService
 * @email 625805189@qq.com
 * TODO 将内容层VISIBLE/GONE的方式
 */

class LoaderVisibilityService : LoaderService() {

    override fun showSuccessView(view: View) {
        preView?.let {
            ensureRootView().removeViewInLayout(it)
        }
        view.run {
            id = loaderId
            visible()
        }
    }

    override fun showLoaderView(view: View) {
        with(ensureRootView()) {
            successCallback?.view?.run {
                if (preView == this) {
                    preView = null
                }
                id = NO_ID
                gone()
            }
            var index = 0
            // 设置id,解决ConstraintLayout布局问题
            view.id = loaderId
            preView?.let {
                index = indexOfChild(it)
                removeViewInLayout(it)
            }
            if (index > 0) {
                addView(view, index, loaderParams)
            } else {
                addView(view, loaderParams)
            }
            preView = view
        }
    }

}