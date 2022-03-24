package com.theone.demo.ui.adapter

import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.theone.demo.R
import com.theone.demo.data.diff.DiffCallback
import com.theone.demo.data.model.bean.IntegralResponse
import com.theone.demo.databinding.ItemIntegralRankBinding
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
 * @describe 积分排行
 * @email 625805189@qq.com
 * @remark
 */
class IntegralRankAdapter : TheBaseQuickAdapter<IntegralResponse, ItemIntegralRankBinding>(
    R.layout.item_integral_rank
){

    init {
        setDiffCallback(DiffCallback.INTEGRAL)
    }

}