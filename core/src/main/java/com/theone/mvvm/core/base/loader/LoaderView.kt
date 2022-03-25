package com.theone.mvvm.core.base.loader

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.theone.mvvm.base.ViewConstructor
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
 * @date 2022-03-25 09:58
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
class LoaderView(constructor: ViewConstructor) : BaseLoaderView(constructor) {

    override fun getLoadingLayout() = R.layout.layout_loading

    override fun getErrorLayout() = R.layout.layout_error

}