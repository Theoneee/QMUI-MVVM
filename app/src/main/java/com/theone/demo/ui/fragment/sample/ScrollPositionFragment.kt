package com.theone.demo.ui.fragment.sample

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.qmuiteam.qmui.widget.QMUITopBarLayout
import com.theone.demo.ui.adapter.IntegralHistoryAdapter
import com.theone.demo.ui.adapter.TestAdapter
import com.theone.mvvm.base.viewmodel.BaseViewModel
import com.theone.mvvm.core.base.fragment.BaseCoreFragment
import com.theone.mvvm.core.databinding.BaseRecyclerPagerFragmentBinding
import com.theone.mvvm.ext.qmui.setTitleWitchBackBtn

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
 * @date 2023-03-16 10:16
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
class ScrollPositionFragment:BaseCoreFragment<BaseViewModel,BaseRecyclerPagerFragmentBinding>() {

    override fun QMUITopBarLayout.initTopBar() {
        setTitleWitchBackBtn("ScrollPosition")
    }

    override fun initView(root: View) {
        val list = mutableListOf<String>()
        for (i in 0 until 100) {
           list.add("item $i")
        }
        getDataBinding().recyclerView.run {
            layoutManager = LinearLayoutManager(requireContext()).apply {
                orientation = LinearLayoutManager.HORIZONTAL
            }
            adapter = TestAdapter(list)
            scrollToPosition(50)
        }
    }

}