package com.theone.mvvm.core.base.fragment

import android.view.View
import com.qmuiteam.qmui.widget.QMUIViewPager
import com.qmuiteam.qmui.widget.tab.QMUITabSegment
import com.theone.mvvm.base.viewmodel.BaseViewModel
import com.theone.mvvm.core.databinding.BaseFragmentIndexBinding
import com.theone.mvvm.core.databinding.BaseTitleTabLayoutBinding
import net.lucode.hackware.magicindicator.MagicIndicator


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
 * @date 2021/3/2 0002
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
abstract class BaseTitleTabFragment<VM : BaseViewModel> :
    BaseTabFragment<VM, BaseTitleTabLayoutBinding>() {

    override fun getDataBindingClass(): Class<BaseTitleTabLayoutBinding> = BaseTitleTabLayoutBinding::class.java

    override fun showTopBar(): Boolean = true

    override fun getViewPager(): QMUIViewPager = getDataBinding().viewPager

    override fun getTabSegment(): QMUITabSegment? = null

    override fun getMagicIndicator(): MagicIndicator = getDataBinding().indicator


}