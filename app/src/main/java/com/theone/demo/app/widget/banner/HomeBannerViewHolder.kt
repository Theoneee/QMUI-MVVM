package com.theone.demo.app.widget.banner

import android.view.View
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatTextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.theone.demo.R
import com.theone.demo.data.model.bean.BannerResponse
import com.zhpan.bannerview.BaseViewHolder

class HomeBannerViewHolder(val view: View) : BaseViewHolder<BannerResponse>(view) {
    override fun bindData(data: BannerResponse?, position: Int, pageSize: Int) {
        val img = itemView.findViewById<ImageView>(R.id.banner_image)
        val title = itemView.findViewById<AppCompatTextView>(R.id.banner_title)
        data?.let {
            title.text = it.title
            Glide.with(view)
                .load(it.imagePath)
                .transition(DrawableTransitionOptions.withCrossFade(500))
                .into(img)
        }
    }

}
