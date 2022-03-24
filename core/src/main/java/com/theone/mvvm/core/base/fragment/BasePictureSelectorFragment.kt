package com.theone.mvvm.core.base.fragment

import android.view.View
import androidx.databinding.ViewDataBinding
import com.chad.library.adapter.base.BaseQuickAdapter
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.config.PictureConfig
import com.luck.picture.lib.config.PictureMimeType
import com.luck.picture.lib.entity.LocalMedia
import com.luck.picture.lib.listener.OnResultCallbackListener
import com.theone.mvvm.core.base.adapter.PictureSelectorAdapter
import com.theone.mvvm.core.base.adapter.getShowPath
import com.theone.mvvm.core.base.viewmodel.BaseListViewModel
import com.theone.mvvm.core.data.entity.ImagePreviewBean
import com.theone.mvvm.core.data.enum.LayoutManagerType
import com.theone.mvvm.core.app.ext.startImagePreview
import com.theone.mvvm.core.app.util.PictureSelectorUtil
import java.util.*

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

    protected open fun getMaxSelectNum(): Int = 9

    override fun getLayoutManagerType(): LayoutManagerType = LayoutManagerType.GRID

    override fun getSpanCount(): Int = 3

    override fun getItemSpace(): Int = 6

    override fun onLazyInit() {
    }

    override fun createAdapter(): BaseQuickAdapter<LocalMedia, *> = PictureSelectorAdapter().apply {
        mSelectMax = getMaxSelectNum()
    }

    protected open fun setMaxSelectNum(max:Int){
        (mAdapter as PictureSelectorAdapter).mSelectMax = max
    }

    override fun initRefreshView() {

    }

    override fun onRefreshDirectly() {

    }

    override fun setRefreshLayoutEnabled(enabled: Boolean) {

    }

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
        if (adapter.getItemViewType(position) == PictureSelectorAdapter.TYPE_ADD) {
            onAddPictureClick()
        } else {
            onSelectImageClick(adapter.getItem(position)as LocalMedia,position)
        }
    }

    protected open fun onAddPictureClick() {
        PictureSelectorUtil.initImageSelector(this,this,maxSelectNum =  getMaxSelectNum(),selectList = getSelectList())
    }

    protected open fun onSelectImageClick(item:LocalMedia,position:Int){
        if (getSelectList().size > 0) {
            val pictureType = item.mimeType
            when (PictureMimeType.getMimeType(pictureType)) {
                PictureConfig.TYPE_VIDEO ->                     // 预览视频
                    PictureSelector.create(mActivity).externalPictureVideo(item.path)
                PictureConfig.TYPE_AUDIO ->                     // 预览音频
                    PictureSelector.create(mActivity).externalPictureAudio(item.path)
                else -> {
                    val images: ArrayList<ImagePreviewBean> =
                        ArrayList<ImagePreviewBean>()
                    for (media in getSelectList()) {
                        val bean = ImagePreviewBean().apply {
                            url = media.getShowPath()
                            mIsVideo =false
                        }
                        images.add(bean)
                    }
                    startImagePreview(mActivity,images = images,position = position)
                }
            }
        }
    }

    override fun onResult(result: List<LocalMedia>) {
        mAdapter.setList(result)
    }

    override fun onCancel() {

    }

    protected open fun getSelectList(): MutableList<LocalMedia> = mAdapter.data

}