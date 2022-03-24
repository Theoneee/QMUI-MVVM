package com.theone.mvvm.core.ui.binding_adapter

import android.graphics.drawable.Drawable
import android.widget.*
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.theone.common.ext.getDrawable
import com.theone.mvvm.core.R

object CoreBindAdapter {

    @BindingAdapter(value = ["imageUrl", "placeHolder"], requireAll = false)
    @JvmStatic
    fun imageUrl(view: ImageView, url: String?, placeholder: Drawable? = null) {
        if (url.isNullOrEmpty()) return
        Glide.with(view.context)
            .load(url)
            .transition(DrawableTransitionOptions().crossFade())
            .apply(
                RequestOptions().placeholder(
                    placeholder
                        ?: getDrawable(
                            view.context,
                            R.drawable.image_place_holder
                        )
                ).centerCrop().diskCacheStrategy(DiskCacheStrategy.ALL)
            )
            .into(view)
    }

}