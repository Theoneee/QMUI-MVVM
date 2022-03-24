package com.theone.mvvm.core.app.ext

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.qmuiteam.qmui.arch.QMUIFragment
import com.theone.common.callback.IImageUrl
import com.theone.common.constant.BundleConstant
import com.theone.common.ext.logI
import com.theone.mvvm.core.ui.activity.ImagePreviewActivity
import com.theone.mvvm.core.data.entity.ImagePreviewBean
import com.theone.mvvm.core.data.entity.ImagePreviewEvent
import com.theone.mvvm.core.ui.fragment.ImagePreviewFragment

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
 * @date 2021-04-25 10:30
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */

fun startImagePreview(activity: Activity,imageUrl:String){
    startImagePreview(activity,images = arrayListOf(ImagePreviewBean(url = imageUrl)))
}

fun QMUIFragment.startImagePreview(imageUrl:String){
    startImagePreviewFragment(ImagePreviewFragment(),images = arrayListOf(ImagePreviewBean(url = imageUrl)))
}

fun <T : IImageUrl> startImagePreview(
    activity: Activity,
    target: Class<*> = ImagePreviewActivity::class.java,
    images: List<T>,
    position: Int = 0,
    needDown: Boolean = true,
    isWhiteTheme: Boolean = false
) {
    if (activity.isDestroyed) return
    val data = ImagePreviewEvent(
        images.getImagePreviewList(),
        position,
        needDown,
        isWhiteTheme
    )
    activity.startActivity(Intent(activity, target).apply {
        putExtra(BundleConstant.DATA, data)
    })
}

fun <T:IImageUrl> QMUIFragment.startImagePreviewFragment(
    fragment: ImagePreviewFragment = ImagePreviewFragment(),
    images: List<T>,
    position: Int = 0,
    needDown: Boolean = true,
    isWhiteTheme: Boolean = false){
    val data = ImagePreviewEvent(
        images.getImagePreviewList(),
        position,
        needDown,
        isWhiteTheme
    )
    startFragment(fragment.apply {
        arguments = Bundle().apply {
            putParcelable(BundleConstant.DATA,data)
        }
    })
}

fun <T:IImageUrl> List<T>.getImagePreviewList():ArrayList<ImagePreviewBean>{
    val previewBeans = mutableListOf<ImagePreviewBean>()
    for (image in this) {
        val bean = ImagePreviewBean().apply {
            with(image) {
                url = getImageUrl()
                mThumbnail = getThumbnail()
                mRefer = getRefer()
                mIsVideo = isVideo()
                mWidth = getWidth()
                mHeight = getHeight()
            }
        }
        bean.toString().logI()
        previewBeans.add(bean)
    }
    return previewBeans as ArrayList<ImagePreviewBean>
}

fun ArrayList<String>.parseImagePreviewBeans():List<ImagePreviewBean>{
    val list = mutableListOf<ImagePreviewBean>()
    forEach {
        list.add(ImagePreviewBean(url = it))
    }
    return list
}

