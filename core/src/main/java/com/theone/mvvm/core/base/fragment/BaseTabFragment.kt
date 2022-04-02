package com.theone.mvvm.core.base.fragment

import android.content.Context
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import com.qmuiteam.qmui.arch.QMUIFragment
import com.qmuiteam.qmui.widget.QMUIViewPager
import com.qmuiteam.qmui.widget.tab.QMUITabBuilder
import com.qmuiteam.qmui.widget.tab.QMUITabSegment
import com.theone.common.ext.getColor
import com.theone.mvvm.base.viewmodel.BaseViewModel
import com.theone.mvvm.core.R
import com.theone.mvvm.core.app.ext.*
import com.theone.mvvm.core.base.adapter.TabFragmentAdapter
import com.theone.mvvm.core.base.viewmodel.BaseRequestViewModel
import com.theone.mvvm.core.data.entity.QMUITabBean
import com.theone.mvvm.core.app.ext.qmui.init
import com.theone.mvvm.core.app.widge.indicator.SkinLinePagerIndicator
import com.theone.mvvm.core.app.widge.indicator.SkinPagerTitleView
import net.lucode.hackware.magicindicator.MagicIndicator
import net.lucode.hackware.magicindicator.ViewPagerHelper
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView
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
 * @describe TAB相关
 * @email 625805189@qq.com
 * @remark
 */
abstract class BaseTabFragment<VM : BaseViewModel, DB : ViewDataBinding> :
    BaseCoreFragment<VM, DB>() {

    private var mTabs: MutableList<QMUITabBean> = mutableListOf()
    private var mFragments: MutableList<QMUIFragment> = mutableListOf()

    protected open lateinit var mPagerAdapter: TabFragmentAdapter

    abstract fun initTabAndFragments(
        tabs: MutableList<QMUITabBean>,
        fragments: MutableList<QMUIFragment>
    )

    abstract fun getViewPager(): QMUIViewPager
    abstract fun getTabSegment(): QMUITabSegment?
    abstract fun getMagicIndicator(): MagicIndicator?

    /**
     * 这个界面不再设置颜色
     * @return Int?
     */
    override fun getRootBackgroundColor(): Int? = null

    /**
     * 如果TAB数据是从网络获取，则返回一个请求的ViewModel，继承 BaseRequestViewModel
     */
    protected open fun getRequestViewModel(): BaseRequestViewModel<*>? = null

    /**
     * TAB的内容是否来自网络
     * @return Boolean
     */
    protected fun isTabFromNet(): Boolean = getRequestViewModel() != null

    /**
     * 是否需要延迟加载数据
     * @return Boolean
     */
    protected open fun isLazyLoadData(): Boolean = true

    /**
     * 在初始化之后的操作
     */
    protected open fun afterInit() {}

    override fun loaderRegisterView(): View?  = if(isTabFromNet()) getViewConstructor().getRootView() else null

    override fun initView(root: View) {
        // 如果Tab的内容不是从网络获取，是否也需要延迟初始化？
        if (!isTabFromNet()) {
            startInit()
        } else if (!isLazyLoadData()) {
            getRequestViewModel()?.requestServer()
        }
    }

    override fun onLazyInit() {
        if (isTabFromNet() && isLazyLoadData()) {
            onPageReLoad()
        }
    }

    override fun onPageReLoad() {
        showLoadingPage()
        getRequestViewModel()?.requestServer()
    }

    override fun createObserver() {
        getRequestViewModel()?.run {
            addLoadingObserve(this)
            getResponseLiveData().observe(this@BaseTabFragment, Observer {
                startInit()
            })
            getErrorLiveData().observe(this@BaseTabFragment, Observer {
                showErrorPage(it) {
                    onPageReLoad()
                }
            })
        }
    }

    private fun startInit() {
        mTabs.clear()
        mFragments.clear()
        initTabAndFragments(mTabs, mFragments)
        initViewPager()
        initTabSegment()
        initMagicIndicator()
        showSuccessPage()
        afterInit()
    }

    private fun initViewPager() {
        mPagerAdapter = TabFragmentAdapter(childFragmentManager, mFragments)
        getViewPager().run {
            adapter = mPagerAdapter
        }
    }

    // QMUISegment

    private fun initTabSegment() {
        getTabSegment()?.run {
            init(getViewPager(), mTabs, tabBuilder().apply { applyTabBuilder() })
        }
    }

    /**
     * 自定义 QMUITabBuilder 重写这个方法
     * @receiver QMUITabSegment
     * @return QMUITabBuilder
     */
    protected open fun QMUITabBuilder.applyTabBuilder() {}

    // MagicIndicator

    private fun initMagicIndicator() {
        getMagicIndicator()?.run {
            navigator = createNavigator()
            ViewPagerHelper.bind(this, getViewPager())
        }
    }

    /**
     * 自定义 CommonNavigator 重写这个方法
     * @return CommonNavigator
     */
    protected open fun createNavigator(): CommonNavigator = CommonNavigator(mActivity).apply {
        scrollPivotX = 0.65f
        isAdjustMode = false
        adapter = createNavigatorAdapter()
    }

    private fun createNavigatorAdapter(): CommonNavigatorAdapter {
        return object : CommonNavigatorAdapter() {

            override fun getCount(): Int = mTabs.size

            override fun getTitleView(context: Context, index: Int): IPagerTitleView {
                return this@BaseTabFragment.getNavTitleView(context, index, mTabs)
            }

            override fun getIndicator(context: Context): IPagerIndicator? {
                return this@BaseTabFragment.getNavIndicator(context)
            }

        }
    }

    /**
     *  自定义 PagerTitleView 重写这个方法
     */
    protected open fun getNavTitleView(
        context: Context,
        index: Int,
        tabs: MutableList<QMUITabBean>
    ): IPagerTitleView {
        return getPagerTitleView(context).init(index, tabs, getViewPager()).initBadgePager(context)
    }

    /**
     * 自定义 PagerIndicator 重写这个方法
     */
    protected open fun getNavIndicator(context: Context): IPagerIndicator? {
        return SkinLinePagerIndicator(context).init()
    }

    /**
     * 普通文字的TitleView(继承自 SimplePagerTitleView) 重写这个方法
     */
    protected open fun getPagerTitleView(
        context: Context
    ): SimplePagerTitleView =
        SkinPagerTitleView(context)


}