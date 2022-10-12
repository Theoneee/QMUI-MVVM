package com.theone.demo.ui.fragment.home

import SuccessCallback
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.getColor
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.qmuiteam.qmui.alpha.QMUIAlphaImageButton
import com.qmuiteam.qmui.kotlin.matchParent
import com.qmuiteam.qmui.qqface.QMUIQQFaceView
import com.qmuiteam.qmui.util.QMUIColorHelper
import com.qmuiteam.qmui.util.QMUIResHelper
import com.theone.common.ext.dp2px
import com.theone.common.ext.invisible
import com.theone.common.ext.showViews
import com.theone.demo.R
import com.theone.demo.app.widget.OffsetLinearLayoutManager
import com.theone.demo.app.widget.banner.HomeBannerAdapter
import com.theone.demo.app.widget.banner.HomeBannerViewHolder
import com.theone.demo.data.model.bean.BannerResponse
import com.theone.demo.ui.fragment.base.BaseArticleFragment
import com.theone.demo.ui.fragment.search.SearchFragment
import com.theone.demo.ui.fragment.web.WebExplorerFragment
import com.theone.demo.viewmodel.HomeViewModel
import com.theone.loader.callback.Callback
import com.theone.mvvm.ext.qmui.updateStatusBarMode
import com.zhpan.bannerview.BannerViewPager
import com.zhpan.bannerview.constants.IndicatorGravity


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
 * @date 2021/2/22 0022
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
class HomeFragment : BaseArticleFragment<HomeViewModel>() {

    private fun showBanner(): Boolean = true

    private var mBannerViewPager: BannerViewPager<BannerResponse, HomeBannerViewHolder>? = null
    private var mMaxOffsetHeight: Float = 0f
    private var isLightMode: Boolean = true
    private var isShow: Boolean = true
    private var mTitleView: QMUIQQFaceView? = null
    private var mSearchBtn: QMUIAlphaImageButton? = null

    override fun isFirstLoadSuccessAutoRefresh(): Boolean = true

    override fun isNeedChangeStatusBarMode(): Boolean = true

    override fun isStatusBarLightMode(): Boolean = if (showBanner()) isLightMode else true

    override fun showTopBar(): Boolean = true

    override fun translucentFull(): Boolean = showBanner()

    override fun isLazyLoadData(): Boolean = false

    override fun loaderDefaultCallback(): Class<out Callback>  = SuccessCallback::class.java

    override fun getItemSpace(): Int = 0

    override fun initView(root: View) {
        root.setBackgroundColor(
            getColor(
                mActivity,
                R.color.qmui_config_color_transparent
            )
        )
        super.initView(root)
        getTopBar()?.run {
            mTitleView = setTitle("首页")
            mSearchBtn =
                addRightImageButton(
                    R.drawable.mz_titlebar_ic_search_light,
                    R.id.topbar_search
                ).apply {
                    setOnClickListener {
                        startFragment(SearchFragment())
                    }
                    invisible()
                }
            if (showBanner())
                setBackgroundAlpha(0)
        }

        // 一个LiveData的测试，猜猜打印结果是什么？
        val liveData = MutableLiveData<Int>()
        liveData.observe(this) {
            Log.e(TAG, "observe1 : $it")
            if (it == 1) {
                liveData.value = 2
            }
        }
        liveData.observe(this) {
            Log.e(TAG, "observe2 : $it")
        }
        liveData.value = 1

    }

    override fun createObserver() {
        super.createObserver()
        if (showBanner()) {
            getViewModel().getBanners().observe(this, Observer { response ->
                mBannerViewPager?.run {
                    // 数据不相同时才刷新
                    if (data.size != response.size) {
                        // 第一次设置时才去改变状态栏
                        if (data.size == 0) {
                            setStatusBarMode(false)
                        }
                        create(response)
                    } else {
                        // 循环遍历是否相同，有一个不同就更新下
                        response.forEachIndexed { index, bannerResponse ->
                            if (bannerResponse != data[index]) {
                                create(response)
                                return@forEachIndexed
                            }
                        }
                        // 一般有新的数据第一个就不一样,所以也可以这样判断下
                        /* if (response[0] != data[0]) {
                            create(response)
                        } */
                    }
                }
            })
        }
    }

    override fun initAdapter() {
        super.initAdapter()
        if (showBanner()) {
            val mBannerHeight = dp2px(260)
            mMaxOffsetHeight = (mBannerHeight - QMUIResHelper.getAttrDimen(
                mActivity,
                R.attr.qmui_topbar_height
            )).toFloat()
            mBannerViewPager =
                BannerViewPager<BannerResponse, HomeBannerViewHolder>(mActivity).apply {
                    layoutParams = ViewGroup.LayoutParams(matchParent, mBannerHeight)
                    adapter = HomeBannerAdapter()
                    setAutoPlay(true)
                    setInterval(3000)
                    setIndicatorGravity(IndicatorGravity.END)
                    setIndicatorSliderColor(
                        getColor(mActivity, R.color.white),
                        QMUIResHelper.getAttrColor(mActivity, R.attr.app_skin_primary_color)
                    )
                    setOnPageClickListener { position: Int ->
                        getViewModel().getBanners().value?.let {
                            startFragment(
                                WebExplorerFragment.newInstance(
                                    it[position]
                                )
                            )
                        }
                    }
                    this@HomeFragment.getAdapter().addHeaderView(this)
                }
        }
    }

    override fun initRecyclerView() {
        super.initRecyclerView()
        if (showBanner())
            getRecyclerView().addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    val offsetLinearLayoutManager =
                        recyclerView.layoutManager as OffsetLinearLayoutManager
                    val y = offsetLinearLayoutManager.computeVerticalScrollOffset(null)
                    val percent: Float = if (y > mMaxOffsetHeight)
                        1.0f
                    else
                        y / mMaxOffsetHeight
                    val isLight = percent > 0.5
                    if (!isLightMode && isLight) {
                        setStatusBarMode(true)
                    } else if (isLightMode && !isLight) {
                        setStatusBarMode(false)
                    }
                    // 更改Title的字体颜色
                    mTitleView?.setTextColor(
                        getColorAlpha(
                            percent,
                            R.color.qmui_config_color_gray_1
                        )
                    )
                    getTopBar()?.run {
                        // 更改底部分割线的颜色
                        updateBottomSeparatorColor(
                            getColorAlpha(
                                percent,
                                R.color.qmui_config_color_separator
                            )
                        )
                        // 更改背景颜色
                        setBackgroundColor(
                            getColorAlpha(
                                percent,
                                R.color.qmui_config_color_white
                            )
                        )
                    }
                }
            })
    }

    /**
     * @TODO 根据百分比获取一个颜色的alpha值
     * @param percent 百分比
     * @param color   颜色
     */
    private fun getColorAlpha(percent: Float, color: Int): Int {
        return QMUIColorHelper.setColorAlpha(
            getColor(mActivity, color),
            percent
        )
    }

    /**
     * 更改状态栏模式
     * @param isLight
     */
    private fun setStatusBarMode(isLight: Boolean) {
        // 只有当Banner数据存在后才进行状态栏的切换
        getViewModel().getBanners().value?.let {
            //显示的时候才做更改
            if (isShow) {
                showViews(mSearchBtn)
                mSearchBtn?.setImageResource(if (isLight) R.drawable.mz_titlebar_ic_search_dark else R.drawable.mz_titlebar_ic_search_light)
                setBannerStatus(!isLight)
                updateStatusBarMode(isLight)
            }
            isLightMode = isLight
        }
    }

    /**
     * 设置轮播状态
     * @param start
     */
    private fun setBannerStatus(start: Boolean) {
        if (showBanner())
            mBannerViewPager?.run {
                if (start) {
                    startLoop()
                } else {
                    stopLoop()
                }
            }
    }

    /**
     * 重写父类方法更换LayoutManager
     */
    override fun getLayoutManager(): RecyclerView.LayoutManager {
        return OffsetLinearLayoutManager(mActivity)
    }

    override fun onPause() {
        super.onPause()
        isShow = false
        setBannerStatus(false)
    }

    override fun onLazyResume() {
        super.onLazyResume()
        isShow = true
        setBannerStatus(true)
    }

}