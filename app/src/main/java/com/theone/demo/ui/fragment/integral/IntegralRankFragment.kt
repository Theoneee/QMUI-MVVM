package com.theone.demo.ui.fragment.integral

import android.os.Bundle
import android.util.SparseArray
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import com.theone.common.constant.BundleConstant
import com.theone.common.ext.getValueNonNull
import com.theone.demo.BR
import com.theone.demo.R
import com.theone.demo.data.net.Url
import com.theone.demo.data.model.bean.BannerResponse
import com.theone.demo.data.model.bean.IntegralResponse
import com.theone.demo.databinding.FragmentIntegralRankBinding
import com.theone.demo.ui.adapter.IntegralRankAdapter
import com.theone.demo.ui.fragment.base.BasePagerListFragment
import com.theone.demo.ui.fragment.web.WebExplorerFragment
import com.theone.demo.viewmodel.IntegralRankViewModel
import com.theone.mvvm.ext.addParams
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
 * @date 2021/3/17 0017
 * @describe 积分排行
 * @email 625805189@qq.com
 * @remark
 */
class IntegralRankFragment private constructor():
    BasePagerListFragment<IntegralResponse, IntegralRankViewModel, FragmentIntegralRankBinding>() {

    companion object {
        fun newInstance(rank: IntegralResponse): IntegralRankFragment {
            return IntegralRankFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(BundleConstant.DATA, rank)
                }
            }
        }
    }

    private val mIntegralRank: IntegralResponse by getValueNonNull(BundleConstant.DATA)

    override fun isFirstLoadSuccessAutoRefresh(): Boolean  = true

    override fun createAdapter(): BaseQuickAdapter<IntegralResponse, *> = IntegralRankAdapter()

    override fun SparseArray<Any>.applyBindingParams() {
        addParams(BR.bean,mIntegralRank)
    }

    override fun initView(root: View) {
        super.initView(root)
        setTitleWitchBackBtn("积分排行")
        getTopBar()?.run {
            addRightImageButton(
                R.drawable.svg_rank_rules,
                R.id.app_integral_rules
            ).setOnClickListener {
                startFragment(
                    WebExplorerFragment.newInstance(
                        BannerResponse(
                            title = "积分规则",
                            url = Url.INTEGRAL_RULES
                        )
                    )
                )
            }
        }
    }

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
    }

    override fun getRecyclerView(): RecyclerView = getDataBinding().recyclerView

    override fun getRefreshLayout(): SwipeRefreshLayout = getDataBinding().swipeRefresh

}