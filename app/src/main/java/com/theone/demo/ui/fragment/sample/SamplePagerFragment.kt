package com.theone.demo.ui.fragment.sample

import android.content.DialogInterface
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.theone.demo.R
import com.theone.demo.app.ext.delay
import com.theone.demo.app.ext.setAdapterAnimation
import com.theone.demo.data.model.bean.ArticleResponse
import com.theone.demo.ui.adapter.ArticleAdapter
import com.theone.demo.viewmodel.AppViewModel
import com.theone.demo.viewmodel.HomeViewModel
import com.theone.mvvm.core.base.fragment.BasePagerPullRefreshFragment
import com.theone.mvvm.core.databinding.BasePullFreshFragmentBinding
import com.theone.mvvm.core.app.widge.pullrefresh.PullRefreshLayout
import com.theone.mvvm.ext.getAppViewModel
import com.theone.mvvm.ext.qmui.setTitleWithBackBtn
import com.theone.mvvm.ext.qmui.showSingleChoiceDialog

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
 * @date 2021-04-08 10:51
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
class SamplePagerFragment :
    BasePagerPullRefreshFragment<ArticleResponse, HomeViewModel, BasePullFreshFragmentBinding>() {

    private val mStyle = arrayOf("QMUIDefaultStyle", "WWLoadingStyle", "FlymeLoadingStyle")

    override fun createAdapter(): BaseQuickAdapter<ArticleResponse, *> = ArticleAdapter()

    override fun initView(root: View) {
        super.initView(root)
        getTopBar()?.run {
            setTitleWithBackBtn("更换下拉刷新的示例", this@SamplePagerFragment)
            setSubTitle(mStyle[PullRefreshLayout.Style])
            addRightImageButton(
                R.drawable.mz_titlebar_ic_more_dark,
                R.id.topbar_right_button1
            ).setOnClickListener {
                showChangeStyleDialog()
            }
        }
    }

    private fun showChangeStyleDialog() {
        context?.showSingleChoiceDialog("选择刷新样式", mStyle, PullRefreshLayout.Style,
            DialogInterface.OnClickListener { dialog, which ->
                dialog.dismiss()
                // TODO 把默认样式放值Application里
                PullRefreshLayout.initStyle(which)
                startFragmentAndDestroyCurrent(SamplePagerFragment())
            })
    }

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
    }

    override fun getRecyclerView(): RecyclerView = getDataBinding().recyclerView

    override fun getRefreshLayout(): PullRefreshLayout = getDataBinding().refreshLayout

    private val mAppVm: AppViewModel by lazy { getAppViewModel<AppViewModel>() }

    /**
     * 第一次请求成功后是否自动刷新（第一次的数据从Cache里获取的)
     */
    private fun isFirstLoadSuccessAutoRefresh() = true

    override fun getItemSpace(): Int = 12

    override fun initAdapter() {
        super.initAdapter()
        mAdapter.setAdapterAnimation(mAppVm.appAnimation.value)
    }

    override fun createObserver() {
        super.createObserver()
        mAppVm.appAnimation.observe(this, Observer {
            mAdapter.setAdapterAnimation(it)
        })
    }

    override fun onFirstLoadSuccess(data: List<ArticleResponse>) {
        super.onFirstLoadSuccess(data)
        if (isFirstLoadSuccessAutoRefresh() && getViewModel().isCache) {
            getViewModel().isCache = false
            delay(200) {
                onRefresh()
            }
        }
    }

    override fun onRefreshSuccess(data: List<ArticleResponse>) {
        mAdapter.getDiffer().submitList(data.toMutableList()) {
            setRefreshLayoutEnabled(true)
            getRecyclerView().scrollToPosition(0)
        }
    }

    override fun onFirstLoadError(errorMsg: String?) {
        if (isFirstLoadSuccessAutoRefresh() && getViewModel().isCache) {
            getViewModel().isCache = false
            onFirstLoading()
        } else {
            super.onFirstLoadError(errorMsg)
        }
    }

}