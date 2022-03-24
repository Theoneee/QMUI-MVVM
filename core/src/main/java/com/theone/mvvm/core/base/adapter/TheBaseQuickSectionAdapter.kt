package com.theone.mvvm.core.base.adapter

import android.util.SparseArray
import androidx.annotation.NonNull
import androidx.core.util.forEach
import androidx.databinding.ViewDataBinding
import com.chad.library.adapter.base.BaseSectionQuickAdapter
import com.chad.library.adapter.base.entity.SectionEntity
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.theone.mvvm.core.BR
import com.theone.mvvm.core.data.entity.BaseSectionEntity
import com.theone.mvvm.ext.addParams


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

    private val bindingParams: SparseArray<Any> = SparseArray()

    protected fun addBindingParams(
        @NonNull variableId: Int,
        @NonNull any: Any
    ) {
        bindingParams.addParams(variableId,any)
    }

    override fun convertHeader(
        helper: BaseDataBindingHolder<BD>,
        item: T
    ) {
        helper.dataBinding?.run {
            this.setVariable(BR.header, item)
            bindingParams.forEach { key, any ->
                setVariable(key, any)
            }
        }

    }

    override fun convert(holder: BaseDataBindingHolder<BD>, item: T) {
        holder.dataBinding?.run {
            this.setVariable(com.theone.mvvm.BR.item, item)
            bindingParams.forEach { key, any ->
                setVariable(key, any)
            }
        }
    }

}