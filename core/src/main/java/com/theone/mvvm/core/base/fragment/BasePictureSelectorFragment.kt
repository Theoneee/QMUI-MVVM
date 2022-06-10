package com.theone.mvvm.core.base.fragment

import SuccessCallback
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.luck.picture.lib.basic.PictureSelector
import com.luck.picture.lib.config.SelectMimeType
import com.luck.picture.lib.decoration.GridSpacingItemDecoration
import com.luck.picture.lib.entity.LocalMedia
import com.luck.picture.lib.interfaces.OnResultCallbackListener
import com.theone.common.ext.dp2px
import com.theone.loader.callback.Callback
import com.theone.mvvm.core.base.adapter.PictureSelectorAdapter
import com.theone.mvvm.core.base.viewmodel.BaseListViewModel
import com.theone.mvvm.core.data.enum.LayoutManagerType
import com.theone.mvvm.core.app.util.glide.GlideEngine
import java.util.*
import kotlin.collections.ArrayList

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
 * @date 2021-04-30 09:24
 * @describe 图片选择基类
 * @email 625805189@qq.com
 * @remark
 */
abstract class BasePictureSelectorFragment<VM : BaseListViewModel<LocalMedia>, DB : ViewDataBinding> :
    BasePagerAdapterFragment<LocalMedia, VM, DB>(),
    OnResultCallbackListener<LocalMedia> {

    override fun getViewModelIndex(): Int = 0

    override fun getDataBindingIndex(): Int = 1

    override fun loaderDefaultCallback(): Class<out Callback> = SuccessCallback::class.java

    protected open fun getMaxSelectNum(): Int = 9

    override fun getLayoutManagerType(): LayoutManagerType = LayoutManagerType.GRID

    override fun getSpanCount(): Int = 4

    override fun getItemSpace(): Int = 10

    protected open fun getSelectList(): ArrayList<LocalMedia> = getAdapter().data as ArrayList<LocalMedia>

    override fun createAdapter(): BaseQuickAdapter<LocalMedia, *> = PictureSelectorAdapter().apply {
        mSelectMax = getMaxSelectNum()
    }

    override fun getItemDecoration(): RecyclerView.ItemDecoration {
        return GridSpacingItemDecoration(getSpanCount(),dp2px(getItemSpace()),false)
    }

    protected open fun setMaxSelectNum(max: Int) {
        (getAdapter() as PictureSelectorAdapter).mSelectMax = max
    }

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
        if (adapter.getItemViewType(position) == PictureSelectorAdapter.TYPE_ADD) {
            onAddPictureClick()
        } else {
            onSelectImageClick(adapter.getItem(position) as LocalMedia, position)
        }
    }

    protected open fun onAddPictureClick() {
        PictureSelector.create(this)
            .openGallery(SelectMimeType.ofAll())
            .setImageEngine(GlideEngine.createGlideEngine())
            .forResult(this)
    }

    protected open fun onSelectImageClick(item: LocalMedia, position: Int) {
        PictureSelector.create(mActivity).openPreview()
            .setImageEngine(GlideEngine.createGlideEngine()).startFragmentPreview(
                position, false,
                getSelectList()
            )
    }


    override fun onResult(result: ArrayList<LocalMedia>?) {
        getAdapter().setList(result)
    }

    override fun onCancel() {

    }

    override fun onLazyInit() {

    }

    override fun initRefreshView() {

    }

    override fun onRefreshDirectly() {

    }

    override fun setRefreshLayoutEnabled(enabled: Boolean) {

    }


}