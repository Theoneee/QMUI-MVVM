package com.theone.mvvm.core.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.luck.picture.lib.interfaces.OnCallbackListener
import com.qmuiteam.qmui.widget.QMUITopBarLayout
import com.qmuiteam.qmui.widget.dialog.QMUIBottomSheet
import com.theone.common.callback.IImageUrl
import com.theone.common.constant.BundleConstant
import com.theone.common.ext.getColor
import com.theone.common.ext.getValueNonNull
import com.theone.mvvm.core.R
import com.theone.mvvm.core.base.fragment.BaseImageSnapFragment
import com.theone.mvvm.core.data.entity.ImagePreviewBean
import com.theone.mvvm.core.data.entity.ImagePreviewEvent
import com.theone.mvvm.core.data.entity.QMUIItem
import com.theone.mvvm.core.databinding.BasePullFreshFragmentBinding
import com.theone.mvvm.core.app.ext.qmui.OnGridBottomSheetItemClickListener
import com.theone.mvvm.core.app.ext.qmui.showGridBottomSheet
import com.theone.mvvm.core.app.ext.showSuccessPage
import com.theone.mvvm.core.app.util.DownloadUtil
import com.theone.mvvm.core.ui.viewmodel.ImagePreviewViewModel
import com.theone.mvvm.core.app.widge.pullrefresh.PullRefreshLayout
import com.theone.mvvm.core.base.loader.callback.Callback
import com.theone.mvvm.core.base.loader.callback.SuccessCallback
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
    OnGridBottomSheetItemClickListener {

    companion object {
        fun newInstance(data: ImagePreviewEvent?): ImagePreviewFragment {
            return ImagePreviewFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(BundleConstant.DATA, data)
                }
            }
        }
    }

    private val mData: ImagePreviewEvent by getValueNonNull(BundleConstant.DATA)
    private val mActions = mutableListOf<QMUIItem>()
    protected open var mClickData: IImageUrl? = null
    private val TAG_SAVE = "下载"

    override fun getDataBindingClass(): Class<BasePullFreshFragmentBinding> =
        BasePullFreshFragmentBinding::class.java

    override fun getViewModelClass(): Class<ImagePreviewViewModel> =
        ImagePreviewViewModel::class.java

    override fun loaderDefaultCallback(): Class<out Callback> = SuccessCallback::class.java

    override fun translucentFull(): Boolean = true

    override fun isLazyLoadData(): Boolean = false

    override fun QMUITopBarLayout.initTopBar() {
        getColor(R.color.black).let {
            setTitle("${mData.position + 1}/${mData.datas.size}").setTextColor(it)
            setSubTitle("").setTextColor(it)
        }
        updateBottomDivider(0,0,0,0)
        addLeftCloseImageBtn(R.drawable.mz_comment_titlebar_ic_close_dark)
    }

    override fun initData() {
        initGridBottomItems(mActions)
        mAdapter.setList(mData.datas)
        Log.e(TAG, "initData: ${mData.datas}" )
        getRecyclerView().scrollToPosition(mData.position)
        setRefreshLayoutEnabled(false)
    }

    override fun onLoadMoreComplete() {
        mAdapter.loadMoreModule.loadMoreEnd(true)
    }

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
    }


//    override fun onImageLongClick(data: ImagePreviewBean, position: Int): Boolean {
//        mClickData = data
//        mActivity.showGridBottomSheet(mActions, listener = this).show()
//        return true
//    }

    override fun onLongPressDownload(media: IImageUrl) {
        mClickData = media
        super.onLongPressDownload(media)
    }

    protected open fun initGridBottomItems(items: MutableList<QMUIItem>) {
        items.add(QMUIItem(TAG_SAVE, R.drawable.svg_operation_save))
    }

    override fun onGridBottomSheetItemClick(dialog: QMUIBottomSheet, itemView: View, tag: String) {
        dialog.dismiss()
        if (tag == TAG_SAVE) {
            mClickData?.let {
                DownloadUtil.startDownload(mActivity, it)
            }
            return
        }
    }

    override fun onScrollChanged(item: ImagePreviewBean, position: Int, total: Int) {
        getTopBar()?.setTitle("${position + 1}/$total")
    }

    override fun getRefreshLayout(): PullRefreshLayout = getDataBinding().refreshLayout

    override fun getRecyclerView(): RecyclerView = getDataBinding().recyclerView


}