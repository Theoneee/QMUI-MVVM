package com.theone.demo.ui.adapter

import android.util.SparseArray
import androidx.core.util.forEach
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.theone.demo.R
import com.theone.demo.data.diff.DiffCallback
import com.theone.demo.data.model.bean.ArticleResponse
import com.theone.demo.databinding.ItemArticleBinding
import com.theone.demo.databinding.ItemScrollPositionBinding
import com.theone.mvvm.core.BR
import com.theone.mvvm.core.base.adapter.TheBaseQuickAdapter


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
 * @date 2021/2/22 0022
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
class TestAdapter(data:MutableList<String>) :  BaseQuickAdapter<String, BaseDataBindingHolder<ItemScrollPositionBinding>>(
    R.layout.item_scroll_position,data
){
    override fun convert(holder: BaseDataBindingHolder<ItemScrollPositionBinding>, item: String) {
        holder.dataBinding?.run {
            this.setVariable(BR.item, item)
            SparseArray<Any>().apply {
                forEach { key, any ->
                    setVariable(key, any)
                }
            }.clear()
        }
    }

}