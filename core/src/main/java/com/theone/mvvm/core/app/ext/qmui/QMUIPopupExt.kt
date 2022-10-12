package com.theone.mvvm.core.app.ext.qmui

import android.content.Context
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.core.view.marginTop
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.qmuiteam.qmui.widget.popup.QMUIPopup
import com.qmuiteam.qmui.widget.popup.QMUIPopups
import com.theone.common.ext.*
import com.theone.mvvm.core.R
import com.theone.mvvm.core.base.adapter.TheBaseQuickAdapter
import com.theone.mvvm.core.databinding.ItemQmuiItemBinding
import com.theone.mvvm.entity.QMUIItem
import com.theone.mvvm.ext.qmui.NO_SET

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
 * @date 2022-10-11 16:21
 * @describe QMUIPopup 相关封装
 * @email 625805189@qq.com
 * @remark
 */

private val mAdapter: BaseQuickAdapter<QMUIItem, *> by lazy {
    object : BaseQuickAdapter<QMUIItem, BaseViewHolder>(R.layout.item_qmui_item) {
        override fun convert(holder: BaseViewHolder, item: QMUIItem) {
            holder.setText(R.id.content, item.getItemTitle())
            holder.getView<ImageView>(R.id.icon).let {
                if (item.getItemNormalImage() == NO_SET) {
                    it.gone()
                } else {
                    it.setImageResource(item.getItemNormalImage())
                    it.visible()
                }
            }
        }
    }
}

fun <T : QMUIItem> Context.createListPopup(
    items: List<T>,
    onItemClickListener: OnItemClickListener,
    adapter: BaseQuickAdapter<QMUIItem, *> = mAdapter,
    width: Int = 400,
    height: Int = 500
): QMUIPopup {

    val recyclerView = RecyclerView(this).apply {
        overScrollMode = View.OVER_SCROLL_NEVER
        layoutManager = LinearLayoutManager(this@createListPopup)
        this.adapter = adapter
        layoutParams = FrameLayout.LayoutParams(matchParent, matchParent).apply {
            setPadding(0, dp2px(12), 0, 0)
        }
    }

    mAdapter.run {
        setOnItemClickListener(onItemClickListener)
        setList(items)
    }

    return QMUIPopup(this, width, height).view(recyclerView)
        .animStyle(QMUIPopup.ANIM_GROW_FROM_CENTER)
        .shadow(true)
        .dimAmount(0.6f)
        .preferredDirection(QMUIPopup.DIRECTION_TOP).shadow(true).edgeProtection(dp2px(10))
        .shadowElevation(dp2px(5), 0.55f)
        .dismissIfOutsideTouch(true)

}

