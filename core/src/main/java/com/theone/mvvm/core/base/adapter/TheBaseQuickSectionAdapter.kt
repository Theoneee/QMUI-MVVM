package com.theone.mvvm.core.base.adapter

import androidx.databinding.ViewDataBinding
import com.chad.library.adapter.base.BaseSectionQuickAdapter
import com.chad.library.adapter.base.entity.SectionEntity
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.theone.mvvm.core.BR


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
 * @date 2021/3/25 09:25
 * @describe 简单封装下DataBinding带有加载更多模块Adapter
 * @email 625805189@qq.com
 * @remark
 */
abstract class TheBaseQuickSectionAdapter<T: SectionEntity, BD : ViewDataBinding>(header: Int, layout: Int) :
    BaseSectionQuickAdapter<T, BaseDataBindingHolder<BD>>(
        header,layout
    ), LoadMoreModule {


    protected open fun BD.applyBindingParams() {}

    override fun convertHeader(
        helper: BaseDataBindingHolder<BD>,
        item: T
    ) {
        helper.dataBinding?.run {
            this.setVariable(BR.header, item)
            applyBindingParams()
        }

    }

    override fun convert(holder: BaseDataBindingHolder<BD>, item: T) {
        holder.dataBinding?.run {
            this.setVariable(com.theone.mvvm.BR.item, item)
            applyBindingParams()
        }
    }

}