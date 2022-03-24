package com.theone.mvvm.core.app.widge

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.theone.common.callback.IImageUrl
import com.theone.common.widget.TheNineGridLayout
import com.theone.mvvm.core.R
import com.theone.mvvm.core.app.ext.load
import com.theone.mvvm.core.app.ext.startImagePreview

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
 * @date 2021-04-25 16:40
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
class TheNineGridView(context: Context, attrs: AttributeSet? = null) :
    TheNineGridLayout(context, attrs) {

    open fun getRatio(): Int = 3

    open fun getMaxHeight(): Int  = 600

    override fun displayOneImage(imageView: ImageView, item: IImageUrl, parentWidth: Int): Boolean {
        if (parentWidth > 0 && item.getWidth() > 0 && item.getHeight() > 0) {
            loadOneImage(imageView, item.getUrl(), item.getWidth(), item.getHeight(), parentWidth)
        }else{
            Glide.with(context).asBitmap().load(item.getUrl()).into(object : SimpleTarget<Bitmap?>() {
                override fun onResourceReady(
                    resource: Bitmap,
                    transition: Transition<in Bitmap?>?
                ) {
                    loadOneImage(imageView, item.getUrl(), resource.width, resource.height, parentWidth)
                }
            })
        }
        return false
    }

    private fun loadOneImage(
        imageView: ImageView,
        url: String,
        width: Int,
        height: Int,
        parentWidth: Int
    ) {
        if (width == 0) return
        val sizes: IntArray =
            getAutoSize(width, height, parentWidth, getRatio(), getMaxHeight())
        val newW = sizes[0]
        val newH = sizes[1]
        setOneImageLayoutParams(imageView, newW, newH)
        imageView.load(url)
    }

    private fun getAutoSize(
        w: Int,
        h: Int,
        parentWidth: Int,
        ratio: Int,
        maxHeight: Int
    ): IntArray {
        val auto = IntArray(2)
        val newW: Int
        var newH: Int
        when {
            h > w * ratio -> { //h:w = 5:3
                newW = parentWidth / 2
                newH = newW * 5 / 3
            }
            h < w -> { //h:w = 2:3
                newW = parentWidth * 2 / 3
                newH = newW * 2 / 3
            }
            else -> { //newH:h = newW :w
                newW = parentWidth / 2
                newH = h * newW / w
            }
        }
        if (newH > maxHeight) newH = maxHeight
        auto[0] = newW
        auto[1] = newH
        return auto
    }

    override fun displayImage(imageView: ImageView, item: IImageUrl) {
        imageView.load(item.getUrl())
    }

    override fun onImageItemClick(view: View, urlList: List<IImageUrl>, position: Int) {
        startImagePreview(context as Activity,images = urlList,position = position)
    }

}