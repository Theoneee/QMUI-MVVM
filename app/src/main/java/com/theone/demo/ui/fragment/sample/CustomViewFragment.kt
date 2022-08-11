package com.theone.demo.ui.fragment.sample

import android.view.View
import com.qmuiteam.qmui.widget.QMUITopBarLayout
import com.theone.demo.databinding.FragmentCustomViewBinding
import com.theone.mvvm.base.fragment.BaseVbFragment
import com.theone.mvvm.base.viewmodel.BaseViewModel
import com.theone.mvvm.core.base.fragment.BaseCoreFragment
import com.theone.mvvm.ext.qmui.setTitleWithBackBtn

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
 * @date 2021-04-14 09:37
 * @describe 自定义View相关
 * @email 625805189@qq.com
 * @remark
 */
class CustomViewFragment : BaseVbFragment<FragmentCustomViewBinding>() {

    override fun QMUITopBarLayout.initTopBar() {
        setTitleWithBackBtn("CustomView", this@CustomViewFragment)
    }

    override fun initView(root: View) {

    }

}