package com.theone.mvvm.core.base.loader.callback

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

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
 * @date 2022-03-27 09:36
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
abstract class Callback {

    abstract fun layoutId(): Int

    var view: View? = null

    fun ensureView(rootView: ViewGroup): View {
        if (null == view) {
            val layoutId = layoutId()
            if (layoutId != 0) {
                rootView.run {
                    view = LayoutInflater.from(context).inflate(layoutId, this, false)
                }
            } else {
                throw IllegalArgumentException("${this.javaClass.simpleName} must have a valid layoutResource")
            }
        }
        return view!!
    }

}