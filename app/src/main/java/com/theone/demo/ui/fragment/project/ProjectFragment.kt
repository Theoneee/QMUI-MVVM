package com.theone.demo.ui.fragment.project

import android.content.Context
import androidx.fragment.app.viewModels
import com.qmuiteam.qmui.arch.QMUIFragment
import com.theone.demo.data.model.bean.ClassifyResponse
import com.theone.demo.viewmodel.ProjectViewModel
import com.theone.mvvm.base.viewmodel.BaseViewModel
import com.theone.mvvm.core.base.fragment.BaseTabInTitleFragment
import com.theone.mvvm.core.data.entity.QMUITabBean
import com.theone.mvvm.core.app.ext.qmui.addTab
import com.theone.mvvm.core.app.widge.indicator.SkinScaleTransitionPagerTitleView
import com.theone.mvvm.core.base.viewmodel.BaseRequestViewModel
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView


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
class ProjectFragment : BaseTabInTitleFragment<BaseViewModel>() {

    private val mRequestVm: ProjectViewModel by viewModels()

    override fun getRequestViewModel(): BaseRequestViewModel<List<ClassifyResponse>>  = mRequestVm

    override fun isLazyLoadData(): Boolean  = false

    override fun initTabAndFragments(
        tabs: MutableList<QMUITabBean>,
        fragments: MutableList<QMUIFragment>
    ) {
        mRequestVm.getResponseLiveData().value?.forEach {
            tabs.addTab(it.name)
            fragments.add(
                ProjectItemFragment.newInstance(
                    it.id
                )
            )
        }
    }

    override fun getPagerTitleView(context: Context): SimplePagerTitleView {
        return SkinScaleTransitionPagerTitleView(context)
    }

    override fun getNavIndicator(context: Context): IPagerIndicator? = null

    override fun isNeedChangeStatusBarMode(): Boolean = true

}