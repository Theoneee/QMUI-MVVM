package com.theone.mvvm.core.app.ext

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.theone.mvvm.core.R

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
 * @date 2021-04-25 16:46
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */

fun ImageView.load(url:Any){
    Glide.with(context)
        .load(url)
        .transition(DrawableTransitionOptions().crossFade())
        .apply(
            RequestOptions().placeholder(
                com.theone.common.ext.getDrawable(
                    context,
                    R.drawable.image_place_holder
                )
            ).centerCrop().diskCacheStrategy(DiskCacheStrategy.ALL)
        )
        .into(this)
}