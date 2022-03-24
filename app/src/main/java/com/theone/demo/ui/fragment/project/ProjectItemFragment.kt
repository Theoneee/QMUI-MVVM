package com.theone.demo.ui.fragment.project

import com.theone.common.constant.BundleConstant
import com.theone.common.ext.bundle
import com.theone.demo.ui.fragment.base.BaseArticleFragment
import com.theone.demo.viewmodel.ProjectItemViewModel


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
class ProjectItemFragment :
    BaseArticleFragment<ProjectItemViewModel>() {

    companion object {
        fun newInstance(id: Int): ProjectItemFragment {
            return  ProjectItemFragment().bundle {
                    putInt(BundleConstant.DATA, id)
            }
        }
    }

    override fun initData() {
        getViewModel().mId = requireArguments().getInt(BundleConstant.DATA)
    }

}