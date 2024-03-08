package com.theone.demo.ui.fragment.project

import android.content.Context
import androidx.lifecycle.Observer
import com.qmuiteam.qmui.arch.QMUIFragment
import com.theone.common.ext.appViewModels
import com.theone.demo.ui.fragment.gzh.WxGzhItemFragment
import com.theone.demo.viewmodel.AppViewModel
import com.theone.demo.viewmodel.ProjectViewModel
import com.theone.mvvm.core.base.fragment.BaseTabInTitleFragment
import com.theone.mvvm.core.data.entity.QMUIItemBean
import com.theone.mvvm.core.app.ext.qmui.addTab
import com.theone.mvvm.core.app.ext.showErrorPage
import com.theone.mvvm.core.app.widge.indicator.SkinScaleTransitionPagerTitleView
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
class ProjectFragment : BaseTabInTitleFragment<ProjectViewModel>() {

    private val mAppVm: AppViewModel by appViewModels()

    override fun isLazyLoadData(): Boolean = false

    override fun initTabAndFragments(
        tabs: MutableList<QMUIItemBean>,
        fragments: MutableList<QMUIFragment>
    ) {
        if (getViewModel().isReload) {
            getViewModel().wxRequest.getResponseLiveData().value?.forEach {
                tabs.addTab(it.name)
                fragments.add(
                    WxGzhItemFragment.newInstance(
                        it.id
                    )
                )
            }
        } else {
            getViewModel().getRequest().getResponseLiveData().value?.forEach {
                tabs.addTab(it.name)
                fragments.add(
                    ProjectItemFragment.newInstance(
                        it.id
                    )
                )
            }
        }
    }


    override fun createObserver() {
        getViewModel().wxRequest.run {
            getResponseLiveData().observe(this@ProjectFragment, Observer {
                startInit()
            })
            getErrorLiveData().observe(this@ProjectFragment, Observer {
                showErrorPage(it.msg) {
                    onPageReLoad()
                }
            })
        }
        getViewModel().getRequest().run {
            getResponseLiveData().observe(this@ProjectFragment, Observer {
                startInit()
            })
            getErrorLiveData().observe(this@ProjectFragment, Observer {
                showErrorPage(it.msg) {
                    onPageReLoad()
                }
            })
        }
        mAppVm.notifyFragment.observe(this) {
            getViewModel().isReload = !getViewModel().isReload
            onPageReLoad()
        }
    }

    override fun getPagerTitleView(context: Context): SimplePagerTitleView {
        return SkinScaleTransitionPagerTitleView(context)
    }

    override fun getNavIndicator(context: Context): IPagerIndicator? = null

    override fun isNeedChangeStatusBarMode(): Boolean = true

}