package com.theone.demo.ui.adapter

import android.util.SparseArray
import com.theone.demo.BR
import com.theone.demo.R
import com.theone.demo.data.diff.DiffCallback
import com.theone.demo.data.model.bean.SystemResponse
import com.theone.demo.databinding.ItemSystemBinding
import com.theone.demo.ui.fragment.category.SystemFragment
import com.theone.mvvm.core.base.adapter.TheBaseQuickAdapter
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
 * @date 2021/2/22 0022
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
class SystemAdapter(val f: SystemFragment) :
    TheBaseQuickAdapter<SystemResponse, ItemSystemBinding>(
        R.layout.item_system
    ) {

    init {
        setDiffCallback(DiffCallback.SYSTEM)
    }

    override fun SparseArray<Any>.bindingParams() {
        addParams(BR.fragment, f)
    }

}