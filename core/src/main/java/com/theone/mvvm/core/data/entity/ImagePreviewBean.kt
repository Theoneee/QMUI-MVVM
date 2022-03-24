package com.theone.mvvm.core.data.entity

import android.os.Parcelable
import com.theone.common.callback.IImageUrl
import kotlinx.android.parcel.Parcelize

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
 * @date 2021-04-25 10:20
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */

@Parcelize
data class ImagePreviewBean(
    var url: String = "",
    var mThumbnail: String? = "",
    var mRefer: String? = "",
    var mIsVideo: Boolean = false,
    var mWidth: Int = 0,
    var mHeight: Int = 0
) : IImageUrl, Parcelable {

    override fun getImageUrl(): String = url

    override fun getThumbnail(): String? = mThumbnail

    override fun getRefer(): String? = mRefer

    override fun isVideo(): Boolean = mIsVideo

    override fun getWidth(): Int = mWidth

    override fun getHeight(): Int = mHeight

    override fun toString(): String {
        return "ImagePreviewBean(url='$url', mThumbnail=$mThumbnail, mRefer=$mRefer, mIsVideo=$mIsVideo, mWidth=$mWidth, mHeight=$mHeight)"
    }


}