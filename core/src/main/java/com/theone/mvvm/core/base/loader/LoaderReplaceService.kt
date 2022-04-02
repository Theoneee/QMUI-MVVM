package com.theone.mvvm.core.base.loader

import android.view.View


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
 * @describe LoaderService2
 * @email 625805189@qq.com
 * TODO 将内容层remove/add的方式
 */

class LoaderReplaceService:LoaderService() {

    override fun showSuccessView(view: View) {
       showLoaderView(view)
    }

    override fun showLoaderView(view: View) {
        with(ensureRootView()) {
            preView?.let {
                // 设置id,解决ConstraintLayout布局问题
                view.id =  it.id
                val index = indexOfChild(it)
                removeViewInLayout(it)
                addView(view, index, successCallback?.view?.layoutParams)
                // 某些机型addView后不执行此方法，这里手动执行下
                requestApplyInsets()
            }
            preView = view
        }
    }

}