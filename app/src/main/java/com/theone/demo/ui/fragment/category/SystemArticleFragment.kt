package com.theone.demo.ui.fragment.category

import android.os.Bundle
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.theone.common.constant.BundleConstant
import com.theone.common.ext.getValueNonNull
import com.theone.demo.data.model.bean.ClassifyResponse
import com.theone.demo.ui.fragment.base.BaseArticleFragment
import com.theone.demo.viewmodel.SystemArticleViewModel
import com.theone.mvvm.ext.qmui.setTitleWithBackBtn


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
 * @describe 体系文章
 * @email 625805189@qq.com
 * @remark
 */
class SystemArticleFragment private constructor():
    BaseArticleFragment<SystemArticleViewModel>() {

    companion object {
        fun newInstance(data: ClassifyResponse): SystemArticleFragment {
            return  SystemArticleFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(BundleConstant.DATA, data)
                }
            }
        }
    }

    private val data:ClassifyResponse by getValueNonNull(BundleConstant.DATA)

    override fun initData() {
        data.let {
            getTopBar()?.setTitleWithBackBtn(it.name,this)
            getViewModel().mId = it.id
        }
    }

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
    }

}