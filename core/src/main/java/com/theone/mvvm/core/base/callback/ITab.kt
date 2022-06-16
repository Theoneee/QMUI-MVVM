package com.theone.mvvm.core.base.callback

import com.qmuiteam.qmui.arch.QMUIFragment
import com.qmuiteam.qmui.widget.QMUIViewPager
import com.qmuiteam.qmui.widget.tab.QMUITabSegment
import com.theone.mvvm.core.base.adapter.TabFragmentAdapter
import com.theone.mvvm.core.data.entity.QMUIItemBean
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
 * @date 2022-06-09 10:00
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
interface ITab {

    fun getViewPager(): QMUIViewPager
    fun getTabSegment(): QMUITabSegment?
    fun getMagicIndicator(): MagicIndicator?
    fun getPagerAdapter(): TabFragmentAdapter

    fun isLazyLoadData(): Boolean = true

    fun initTabAndFragments(
        tabs: MutableList<QMUIItemBean>,
        fragments: MutableList<QMUIFragment>
    )

}