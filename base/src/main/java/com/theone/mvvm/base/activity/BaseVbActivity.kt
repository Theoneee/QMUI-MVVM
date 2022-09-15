package com.theone.mvvm.base.activity

import android.view.View
import androidx.viewbinding.ViewBinding
import com.theone.mvvm.base.IViewBinding
import com.theone.mvvm.base.ViewConstructor

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
 * @date 2022-06-16 08:48
 * @describe ViewBinding基类
 * @email 625805189@qq.com
 * @remark  如果不需要布局文件生成对应的Binding类，可以在根元素加入：
 *          tools:viewBindingIgnore="true"
 *
 */
abstract class BaseVbActivity<VB : ViewBinding> : BaseQMUIActivity(), IViewBinding<VB> {

    override fun getContentViewFactory(): ViewConstructor.Factory {
        return ViewConstructor.ViewBindingFactory(getViewBindingClass())
    }

    override fun getViewBinding(): VB =
        (getViewConstructor().getFactory() as ViewConstructor.ViewBindingFactory<*>).getViewBinding() as VB

}