package com.theone.mvvm.core.base.adapter

import android.util.SparseArray
import androidx.core.util.forEach
import androidx.databinding.ViewDataBinding
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.entity.MultiItemEntity
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
abstract class TheBaseMultiItemQuickAdapter<T :MultiItemEntity, BD : ViewDataBinding>() :
    BaseMultiItemQuickAdapter<T, BaseDataBindingHolder<BD>>(), LoadMoreModule {

    protected open fun BD.applyBindingParams(){}

    override fun convert(holder: BaseDataBindingHolder<BD>, item: T) {
        holder.dataBinding?.run {
            this.setVariable(BR.item, item)
            applyBindingParams()
        }
    }

}