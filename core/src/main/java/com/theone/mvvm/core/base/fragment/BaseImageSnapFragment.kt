package com.theone.mvvm.core.base.fragment

import android.view.View
import android.view.animation.Animation
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.luck.picture.lib.interfaces.OnCallbackListener
import com.qmuiteam.qmui.util.QMUIDirection
import com.qmuiteam.qmui.util.QMUIViewHelper
import com.qmuiteam.qmui.widget.QMUITopBarLayout
import com.theone.common.callback.IImageUrl
import com.theone.common.ext.getColor
import com.theone.common.ext.isVisible
import com.theone.mvvm.core.R
import com.theone.mvvm.core.base.adapter.ThePicturePreviewAdapter
import com.theone.mvvm.core.base.adapter.holder.TheBasePreviewHolder
import com.theone.mvvm.core.base.viewmodel.BaseListViewModel
import com.theone.mvvm.ext.qmui.addLeftCloseImageBtn
import com.theone.mvvm.ext.qmui.updateStatusBarMode

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
 * @date 2021-04-25 09:14
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
abstract class BaseImageSnapFragment<T : IImageUrl, VM : BaseListViewModel<T>, DB : ViewDataBinding> :
    BasePagerPullRefreshFragment<T, VM, DB>(), TheBasePreviewHolder.OnPreviewEventListener,
    Animation.AnimationListener {

    private var isLightModel: Boolean = true

    abstract fun onScrollChanged(item: T, position: Int, total: Int)

    /**
     * @return 滑动方向
     */
    protected open fun getOrientation(): Int = RecyclerView.HORIZONTAL

    override fun isNeedChangeStatusBarMode(): Boolean = true

    override fun isStatusBarLightMode(): Boolean = isLightModel

    override fun translucentFull(): Boolean = true

    override fun getRootBackgroundColor(): Int = R.color.qmui_config_color_pure_black

    override fun createAdapter(): BaseQuickAdapter<T, *> = ThePicturePreviewAdapter<T>(this)

    override fun QMUITopBarLayout.initTopBar() {
        setBackgroundAlpha(0)
        addLeftCloseImageBtn(R.drawable.mz_comment_titlebar_ic_close_light)
        getColor(R.color.white).let {
            setTitle("").setTextColor(it)
            setSubTitle("").setTextColor(it)
        }
        updateBottomDivider(0, 0, 0, 0)
    }

    override fun initAdapter() {
        with(getAdapter()) {
            if (this is LoadMoreModule) {
                loadMoreModule.loadMoreView = getAdapterLoadMoreView()
                loadMoreModule.setOnLoadMoreListener(this@BaseImageSnapFragment)
            }
            animationEnable = false
        }
    }

    override fun initRecyclerView() {
        super.initRecyclerView()
        getRecyclerView().let {
            PagerSnapHelper().attachToRecyclerView(it)
            it.addOnScrollListener(getScrollListener())
        }
    }

    override fun onFirstLoadSuccess(data: List<T>) {
        super.onFirstLoadSuccess(data)
        isLightModel = false
        updateStatusBarMode(false)
    }

    protected open fun getScrollListener(): RecyclerView.OnScrollListener {
        return object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager
                val position = linearLayoutManager.findFirstVisibleItemPosition()
                if (getAdapter().data.size > position) {
                    onScrollChanged(
                        getAdapter().getItem(position) as T,
                        position,
                        getAdapter().data.size
                    )
                }
            }

        }
    }

    override fun getLayoutManager(): RecyclerView.LayoutManager {
        return LinearLayoutManager(mActivity, getOrientation(), false)
    }

    /**
     * 更改进出动画效果 QMUIFragment提供
     * @return
     */
    override fun onFetchTransitionConfig(): TransitionConfig = SCALE_TRANSITION_CONFIG

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {

    }

    override fun onPreviewVideoTitle(videoName: String?) {
    }

    override fun onPreviewLongClick(media: IImageUrl,position: Int) {
    }

    override fun onPreviewLoadComplete(width: Int, height: Int, call: OnCallbackListener<Boolean>?) {
    }

    override fun onPreviewLoadError() {
    }

    override fun onPreviewClick(media: IImageUrl,position: Int) {
        getTopBar()?.run {
            if (isVisible()) {
                QMUIViewHelper.slideOut(
                    this,
                    500,
                    this@BaseImageSnapFragment,
                    true,
                    QMUIDirection.BOTTOM_TO_TOP
                )
            } else {
                QMUIViewHelper.slideIn(
                    this,
                    500,
                    this@BaseImageSnapFragment,
                    true,
                    QMUIDirection.TOP_TO_BOTTOM
                )
            }
        }
    }

    override fun onDestroy() {
        (getAdapter() as ThePicturePreviewAdapter).destroy()
        super.onDestroy()
    }

    override fun onAnimationStart(animation: Animation?) {
    }

    override fun onAnimationEnd(animation: Animation?) {
        getTopBar()?.let {
            updateStatusBarMode(it.isVisible())
        }
    }

    override fun onAnimationRepeat(animation: Animation?) {
    }

}