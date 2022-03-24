package com.theone.mvvm.core.ui.fragment

import com.qmuiteam.qmui.widget.QMUIProgressBar
import com.qmuiteam.qmui.widget.QMUITopBarLayout
import com.qmuiteam.qmui.widget.webview.QMUIWebViewContainer
import com.theone.mvvm.base.viewmodel.BaseViewModel
import com.theone.mvvm.core.base.fragment.BaseWebFragment
import com.theone.mvvm.core.databinding.FragmentWebExploererBinding

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
 * @date 2021-12-14 16:42
 * @describe 给一个默认的WebFragment
 * @email 625805189@qq.com
 * @remark
 */
class SampleWebFragment internal constructor():BaseWebFragment<BaseViewModel,FragmentWebExploererBinding>() {

    override fun getWebContainer(): QMUIWebViewContainer = getDataBinding().mWebContainer

    override fun getProgressBar(): QMUIProgressBar = getDataBinding().progressbar

    override fun getTopBar(): QMUITopBarLayout = getDataBinding().topbar

}