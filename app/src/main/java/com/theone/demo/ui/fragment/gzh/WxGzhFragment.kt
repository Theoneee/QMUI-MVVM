package com.theone.demo.ui.fragment.gzh

import android.util.Log
import androidx.fragment.app.viewModels
import com.qmuiteam.qmui.arch.QMUIFragment
import com.theone.demo.data.model.bean.ClassifyResponse
import com.theone.demo.viewmodel.WxGzhRequestViewModel
import com.theone.mvvm.base.viewmodel.BaseViewModel
import com.theone.mvvm.core.data.entity.QMUITabBean
import com.theone.mvvm.core.app.ext.qmui.addTab
import com.theone.mvvm.core.base.fragment.BaseTabInTitleFragment
import com.theone.mvvm.core.base.viewmodel.BaseRequestViewModel


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
 * @date 2021/3/4 0004
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
class WxGzhFragment:BaseTabInTitleFragment<BaseViewModel>() {

    private val mRequestVm:WxGzhRequestViewModel by viewModels()

    override fun isNeedChangeStatusBarMode(): Boolean  = true

    /**
     * 这里如果为了方便，直接把泛型里的ViewModel填入WxGzhRequestViewModel，然后这里直接返回 mViewModel
     */
    override fun getRequestViewModel(): BaseRequestViewModel<List<ClassifyResponse>> = mRequestVm

    override fun isLazyLoadData(): Boolean  = false

    override fun initTabAndFragments(
        tabs: MutableList<QMUITabBean>,
        fragments: MutableList<QMUIFragment>
    ) {
        Log.e(TAG, "initTabAndFragments: " )
        for (data in mRequestVm.getResponseLiveData().value!!) {
            tabs.addTab(data.name)
            fragments.add(
                WxGzhItemFragment.newInstance(
                    data.id
                )
            )
        }
    }

}