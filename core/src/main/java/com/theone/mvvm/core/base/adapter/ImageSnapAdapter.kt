package com.theone.mvvm.core.base.adapter

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.net.Uri
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.bumptech.glide.load.resource.gif.GifDrawable
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.transition.Transition
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.luck.picture.lib.widget.longimage.ImageSource
import com.luck.picture.lib.widget.longimage.SubsamplingScaleImageView
import com.qmuiteam.qmui.util.QMUIResHelper
import com.qmuiteam.qmui.widget.QMUIProgressBar
import com.theone.common.callback.IImageUrl
import com.theone.common.ext.*
import com.theone.mvvm.core.R
import com.theone.mvvm.core.databinding.ItemImageSnapBinding
import com.theone.mvvm.core.app.util.glide.GlideProgressInterceptor
import com.theone.mvvm.core.app.util.imagepreview.FileTarget
import com.theone.mvvm.core.app.util.imagepreview.ImageLoader
import com.theone.mvvm.core.app.util.imagepreview.ImageUtil
import jp.wasabeef.glide.transformations.BlurTransformation
import java.io.File

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
 * @date 2021-04-25 09:16
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
class ImageSnapAdapter<T : IImageUrl>(private val onImageSnapClickListener: OnImageSnapClickListener<T>? = null) :
    TheBaseQuickAdapter<T, ItemImageSnapBinding>(R.layout.item_image_snap) {

    private val minScale = 0.5f // 最小缩放倍数
    private val mediumScale = 2.0f // 中等缩放倍数
    private val maxScale = 3.0f // 最大缩放倍数

    var isWhiteBg: Boolean = true

    override fun convert(holder: BaseDataBindingHolder<ItemImageSnapBinding>, item: T) {
        super.convert(holder, item)
        holder.dataBinding?.run {
            progressbar.run {
                setBackgroundColor(getColor(if (isWhiteBg) R.color.qmui_config_color_gray_9 else R.color.white))
                gone()
            }
            photoView.run {
                destroyDrawingCache()
                setImageBitmap(null)
                isEnabled = true
            }
            longImg.run {
                destroyDrawingCache()
                recycle()
            }
            ivPlay.gone()
            val url: Any = if (item.getRefer().isNullOrEmpty()) {
                item.getImageUrl()
            } else {
                GlideUrl(
                    item.getImageUrl(), LazyHeaders.Builder()
                        .addHeader("Referer", item.getRefer()!!)
                        .build()
                )
            }
            val cacheFile: File? = ImageLoader.getGlideCacheFile(context,url.toString())
            if (null !=cacheFile &&cacheFile.exists()) {
                val isCacheIsGif: Boolean =
                    ImageUtil.isGifImageWithMime(cacheFile.absolutePath)
                val imagePath = cacheFile.absolutePath
                if (isCacheIsGif) {
                    loadGifImageSpec(imagePath, photoView, longImg, progressbar,container)
                } else {
                    loadImageSpec(
                        imagePath,
                        photoView,
                        longImg,
                        progressbar,
                        ivPlay,
                        container,
                        item.isVideo()
                    )
                }
            } else loadImage(url, photoView, longImg, progressbar, 1, ivPlay,container, item.isVideo())

            setListener(item, holder.adapterPosition,photoView, longImg, ivPlay)
        }
    }

    private fun setListener(item: T,position: Int, vararg views: View) {
        onImageSnapClickListener?.run {
            for (view in views) {
                if (view.id == R.id.iv_play) {
                    view.setOnClickListener {
                        onVideoClick(item,position)
                    }
                } else {
                    view.setOnClickListener {
                        onImageClick(item,position)
                    }
                    view.setOnLongClickListener {
                        onImageLongClick(item,position)
                    }
                }
            }
        }
    }

    @SuppressLint("CheckResult")
    private fun loadImage(
        url: Any?,
        imageGif: ImageView,
        imageView: SubsamplingScaleImageView,
        progressBar: QMUIProgressBar,
        count: Int,
        ivPlay: ImageView,
        container: View,
        isVideo: Boolean
    ) {
        progressBar.gone()
        GlideProgressInterceptor.addListener(
            url
        ) { progress, success -> progressBar.progress = progress }
        Glide.with(context).downloadOnly().load(url).addListener(object : RequestListener<File> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<File>?,
                isFirstResource: Boolean
            ): Boolean {
                if (count > 3) {
                    GlideProgressInterceptor.removeListener(url)
                    loadFail(imageView, imageGif, ivPlay, progressBar, e)
                } else
                    loadImage(url, imageGif, imageView, progressBar, count + 1, ivPlay,container, isVideo)
                return true
            }

            override fun onResourceReady(
                resource: File,
                model: Any?,
                target: Target<File>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean {
                url?.removeListener()
                loadSuccess(resource,imageView,imageGif,progressBar,ivPlay,container,isVideo)
                return true
            }
        })
            .into(object : FileTarget(){
                override fun onLoadStarted(placeholder: Drawable?) {
                    super.onLoadStarted(placeholder)
                    progressBar.visible()
                }
            })
    }

    private fun loadSuccess(
        resource: File,
        imageView: SubsamplingScaleImageView,
        imageGif: ImageView,
        progressBar: QMUIProgressBar,
        ivPlay: ImageView,
        container:View,
        isVideo: Boolean
    ) {
        val imagePath = resource.absolutePath
        val isCacheIsGif = ImageUtil.isGifImageWithMime(imagePath)
        if (isCacheIsGif) {
            loadGifImageSpec(imagePath, imageGif, imageView, progressBar,container)
        } else {
            loadImageSpec(imagePath, imageGif, imageView, progressBar, ivPlay,container, isVideo)
        }
    }

    private fun loadFail(
        imageView: SubsamplingScaleImageView,
        imageGif: ImageView,
        ivPlay: ImageView,
        progressBar: QMUIProgressBar,
        e: GlideException?
    ) {
        goneViews(progressBar, imageView, ivPlay)
        imageGif.run {
            visible()
            setLoadFailDrawable()
            isEnabled = false
        }
    }

    @SuppressLint("CheckResult")
    private fun loadGifImageSpec(imagePath: String, imageGif: ImageView, imageView: SubsamplingScaleImageView, progressBar: QMUIProgressBar,container:View){
        showViews(imageGif,progressBar)
        imageView.gone()
        Glide.with(context)
            .asGif()
            .load(imagePath)
            .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.RESOURCE)
                .error(getFailDrawable()))
            .listener(object :RequestListener<GifDrawable>{
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<GifDrawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    progressBar.reset()
                    imageGif.setLoadFailDrawable()
                    imagePath.removeListener()
                    return false
                }

                override fun onResourceReady(
                    resource: GifDrawable?,
                    model: Any?,
                    target: Target<GifDrawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    resource?.let {
                        progressBar.reset()
                        imagePath.removeListener()
                    }
                    return false
                }

            })
            .into(imageGif)
        container.loadBg(imagePath)
    }

    private fun loadImageSpec(
        imagePath: String,
        imageGif: ImageView,
        imageView: SubsamplingScaleImageView,
        progressBar: QMUIProgressBar,
        ivPlay: ImageView,
        container:View,
        isVideo: Boolean
    ) {
        if (isVideo) {
            imageView.gone()
            loadVideoImageSpec(imagePath, imageGif, progressBar, ivPlay)
        } else {
            imageGif.gone()
            showViews(imageView, progressBar)
            setImageSpec(imagePath, imageView)
            imageView.orientation = SubsamplingScaleImageView.ORIENTATION_USE_EXIF
            val imageSource = ImageSource.uri(Uri.fromFile(File(imagePath)))
            if (ImageUtil.isBmpImageWithMime(imagePath)) {
                imageSource.tilingDisabled()
            }
            imageView.setImage(imageSource)
            imageView.setOnImageEventListener(object :
                SubsamplingScaleImageView.OnImageEventListener {
                override fun onImageLoaded() {
                }

                override fun onReady() {
                    progressBar.reset()
                    container.loadBg(imagePath)
                }

                override fun onTileLoadError(e: Exception?) {
                }

                override fun onPreviewReleased() {
                }

                override fun onImageLoadError(e: Exception?) {
                    progressBar.reset()
                    imageGif.visibility = View.VISIBLE
                    imageGif.setLoadFailDrawable()
                }

                override fun onPreviewLoadError(e: Exception?) {
                }

            })
        }
    }

    private fun setImageSpec(imagePath: String, imageView: SubsamplingScaleImageView) {
        val isLongImage = ImageUtil.isLongImage(context, imagePath)
        with(imageView) {
            if (isLongImage) {
                setMinimumScaleType(SubsamplingScaleImageView.SCALE_TYPE_CENTER_CROP)
                minScale = ImageUtil.getLongImageMinScale(context, imagePath)
                maxScale = ImageUtil.getLongImageMaxScale(context, imagePath)
                setDoubleTapZoomScale(
                    ImageUtil.getLongImageMaxScale(
                        context,
                        imagePath
                    )
                )
            } else {
                val isWideImage = ImageUtil.isWideImage(context, imagePath)
                val isSmallImage = ImageUtil.isSmallImage(context, imagePath)
                when {
                    isWideImage -> {
                        setMinimumScaleType(SubsamplingScaleImageView.SCALE_TYPE_CENTER_INSIDE)
                        minScale = this@ImageSnapAdapter.minScale
                        maxScale = this@ImageSnapAdapter.maxScale
                        setDoubleTapZoomScale(
                            ImageUtil.getWideImageDoubleScale(
                                context,
                                imagePath
                            )
                        )
                    }
                    isSmallImage -> {
                        setMinimumScaleType(SubsamplingScaleImageView.SCALE_TYPE_CUSTOM)
                        minScale = ImageUtil.getSmallImageMinScale(context, imagePath)
                        maxScale = ImageUtil.getSmallImageMaxScale(context, imagePath)
                        setDoubleTapZoomScale(
                            ImageUtil.getSmallImageMaxScale(
                                context,
                                imagePath
                            )
                        )
                    }
                    else -> {
                        setMinimumScaleType(SubsamplingScaleImageView.SCALE_TYPE_CENTER_INSIDE)
                        minScale = this@ImageSnapAdapter.minScale
                        maxScale = this@ImageSnapAdapter.maxScale
                        setDoubleTapZoomScale(mediumScale)
                    }
                }
            }
        }
    }

    @SuppressLint("CheckResult")
    private fun loadVideoImageSpec(
        imagePath: String,
        imageGif: ImageView,
        progressBar: QMUIProgressBar,
        ivPlay: ImageView
    ) {
        progressBar.visible()
        GlideProgressInterceptor.addListener(
            imagePath
        ) { progress, success -> progressBar.progress = progress }
        Glide.with(context)
            .load(imagePath)
            .apply(
                RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.RESOURCE)
                    .error(getFailDrawable())
            )
            .listener(object :RequestListener<Drawable>{
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    progressBar.reset()
                    imagePath.removeListener()
                    imageGif.setLoadFailDrawable()
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                   progressBar.reset()
                    ivPlay.visible()
                    imagePath.removeListener()
                    return false
                }
            })
            .into(imageGif)
    }

    private fun View.loadBg(url:String){
        Glide.with(context)
            .load(url)
            .transform(BlurTransformation(10, 25))
            .into(object : SimpleTarget<Drawable>() {
                override fun onResourceReady(
                    resource: Drawable,
                    transition: Transition<in Drawable>?
                ) {
                    background = resource
                }
            })
    }

    private fun getFailDrawable(): Drawable? {
        return QMUIResHelper.getAttrDrawable(
            context,
            R.attr.app_skin_glide_fail_drawable
        )
    }

    fun ImageView.setLoadFailDrawable() {
        setImageDrawable(getFailDrawable())
    }

    private fun QMUIProgressBar.reset() {
        progress = 0
        gone()
    }

    private fun Any.removeListener(){
        GlideProgressInterceptor.removeListener(this)
    }

    interface OnImageSnapClickListener<T : IImageUrl> {
        fun onImageClick(data: T,position:Int)
        fun onVideoClick(data: T,position:Int)
        fun onImageLongClick(data: T,position:Int): Boolean
    }
}