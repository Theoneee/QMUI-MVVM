package com.theone.demo.ui.fragment.gzh

import android.util.Log
import com.qmuiteam.qmui.arch.QMUIFragment
import com.theone.demo.viewmodel.WxGzhRequestViewModel
import com.theone.mvvm.core.data.entity.QMUIItemBean
import com.theone.mvvm.core.app.ext.qmui.addTab
import com.theone.mvvm.core.base.fragment.BaseTabInTitleFragment


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
 * @date 2021/3/4 0004
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
class WxGzhFragment:BaseTabInTitleFragment<WxGzhRequestViewModel>() {

    override fun isNeedChangeStatusBarMode(): Boolean  = true

    override fun isLazyLoadData(): Boolean  = false

    override fun initTabAndFragments(
        tabs: MutableList<QMUIItemBean>,
        fragments: MutableList<QMUIFragment>
    ) {
        for (data in getViewModel().getRequest().getResponseLiveData().value!!) {
            tabs.addTab(data.name)
            fragments.add(
                WxGzhItemFragment.newInstance(
                    data.id
                )
            )
        }
    }

}