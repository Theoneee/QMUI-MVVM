package com.theone.demo.ui.fragment.search

import android.os.Bundle
import android.view.View
import com.theone.demo.ui.fragment.base.BaseArticleFragment
import com.theone.demo.viewmodel.SearchResultModel
import com.theone.mvvm.ext.qmui.setTitleWithBackBtn
import com.theone.common.constant.BundleConstant
import com.theone.common.ext.getValueNonNull


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
class SearchResultFragment private constructor() :
    BaseArticleFragment<SearchResultModel>() {

    companion object {
        fun newInstance(key: String): SearchResultFragment {
            return SearchResultFragment().apply {
                arguments = Bundle().apply {
                    putString(BundleConstant.DATA, key)
                }
            }
        }
    }

    private val mKey: String by getValueNonNull(BundleConstant.DATA)

    override fun initView(root: View) {
        getViewModel().mKey = mKey
        super.initView(root)
        getTopBar()?.setTitleWithBackBtn(mKey, this)
    }

    /**
     * 更改进出动画效果 QMUIFragment提供
     */
    override fun onFetchTransitionConfig(): TransitionConfig = SCALE_TRANSITION_CONFIG

}