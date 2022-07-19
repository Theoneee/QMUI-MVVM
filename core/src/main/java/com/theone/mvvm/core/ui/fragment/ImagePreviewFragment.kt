package com.theone.mvvm.core.ui.fragment

import SuccessCallback
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import androidx.recyclerview.widget.RecyclerView
import com.qmuiteam.qmui.widget.QMUITopBarLayout
import com.qmuiteam.qmui.widget.dialog.QMUIBottomSheet
import com.theone.common.callback.IImageUrl
import com.theone.common.constant.BundleConstant
import com.theone.common.ext.getColor
import com.theone.common.ext.getValueNonNull
import com.theone.loader.callback.Callback
import com.theone.mvvm.core.R
import com.theone.mvvm.core.base.fragment.BaseImageSnapFragment
import com.theone.mvvm.core.data.entity.ImagePreviewBean
import com.theone.mvvm.core.data.entity.ImagePreviewEvent
import com.theone.mvvm.core.data.entity.QMUIItemBean
import com.theone.mvvm.core.databinding.BasePullFreshFragmentBinding
import com.theone.mvvm.core.app.ext.qmui.OnGridBottomSheetItemClickListener
import com.theone.mvvm.core.app.ext.qmui.showGridBottomSheet
import com.theone.mvvm.core.app.util.DownloadUtil
import com.theone.mvvm.core.ui.viewmodel.ImagePreviewViewModel
import com.theone.mvvm.core.app.widge.pullrefresh.PullRefreshLayout
import com.theone.mvvm.ext.qmui.addLeftCloseImageBtn

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
 * @date 2021-04-25 10:18
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
open class ImagePreviewFragment :
    BaseImageSnapFragment<ImagePreviewBean, ImagePreviewViewModel, BasePullFreshFragmentBinding>(),
    OnGridBottomSheetItemClickListener<QMUIItemBean> {

    companion object {
        fun newInstance(data: ImagePreviewEvent?) = ImagePreviewFragment().apply {
            arguments = Bundle().apply {
                putParcelable(BundleConstant.DATA, data)
            }
        }
    }

    private val mData: ImagePreviewEvent by getValueNonNull(BundleConstant.DATA)
    private val mActions = mutableListOf<QMUIItemBean>()
    protected open var mClickData: IImageUrl? = null
    private val TAG_SAVE = "下载"

    override fun isStatusBarLightMode(): Boolean = false

    override fun getDataBindingClass(): Class<BasePullFreshFragmentBinding> =
        BasePullFreshFragmentBinding::class.java

    override fun getViewModelClass(): Class<ImagePreviewViewModel> =
        ImagePreviewViewModel::class.java

    override fun loaderDefaultCallback(): Class<out Callback> = SuccessCallback::class.java

    override fun translucentFull(): Boolean = true

    override fun isLazyLoadData(): Boolean = false

    override fun QMUITopBarLayout.initTopBar() {
        setBackgroundAlpha(0)
        addLeftCloseImageBtn(R.drawable.mz_comment_titlebar_ic_close_light)
        getColor(R.color.white).let {
            setTitle("${mData.position + 1}/${mData.datas.size}").setTextColor(it)
        }
        updateBottomDivider(0, 0, 0, 0)
    }

    override fun initData() {
        initGridBottomItems(mActions)
        getAdapter().setList(mData.datas)
        getRecyclerView().scrollToPosition(mData.position)
        setRefreshLayoutEnabled(false)
    }

    override fun onLoadMoreComplete() {
        getAdapter().loadMoreModule.loadMoreEnd(true)
    }

    override fun onPreviewLongClick(media: IImageUrl, position: Int) {
        mClickData = media
        mActivity.showGridBottomSheet(mActions, listener = this).show()
    }

    protected open fun initGridBottomItems(items: MutableList<QMUIItemBean>) {
        items.add(QMUIItemBean(TAG_SAVE, R.drawable.svg_operation_save))
    }

    override fun onGridBottomSheetItemClick(
        dialog: QMUIBottomSheet,
        itemView: View,
        item: QMUIItemBean
    ) {
        dialog.dismiss()
        if (item.getItemTitle() == TAG_SAVE) {
            DownloadUtil.startDownload(mActivity, mClickData!!)
            return
        }
    }

    override fun onScrollChanged(item: ImagePreviewBean, position: Int, total: Int) {
        getTopBar()?.setTitle("${position + 1}/$total")
    }

    override fun onAnimationEnd(animation: Animation?) {

    }

    override fun getRefreshLayout(): PullRefreshLayout = getDataBinding().refreshLayout

    override fun getRecyclerView(): RecyclerView = getDataBinding().recyclerView

}