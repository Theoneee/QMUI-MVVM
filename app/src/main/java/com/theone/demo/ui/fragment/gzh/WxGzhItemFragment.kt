package com.theone.demo.ui.fragment.gzh

import android.os.Bundle
import com.theone.demo.ui.fragment.base.BaseArticleFragment
import com.theone.demo.viewmodel.WxGzhItemViewModel


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
 * @date 2021/3/3 0003
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
class WxGzhItemFragment :
    BaseArticleFragment<WxGzhItemViewModel>() {

    companion object {
        fun newInstance(id: Int): WxGzhItemFragment {
            val fragment = WxGzhItemFragment()
            val bundle = Bundle()
            bundle.putInt("DATA", id)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun initData() {
        val id = requireArguments().getInt("DATA")
        getViewModel().mId = id
    }

}