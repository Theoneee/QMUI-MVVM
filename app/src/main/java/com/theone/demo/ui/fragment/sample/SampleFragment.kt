package com.theone.demo.ui.fragment.sample

import android.view.View
import com.qmuiteam.qmui.widget.grouplist.QMUICommonListItemView
import com.theone.demo.databinding.FragmentSampleBinding
import com.theone.mvvm.ext.qmui.addToGroup
import com.theone.mvvm.base.viewmodel.BaseViewModel
import com.theone.mvvm.core.base.fragment.BaseCoreFragment
import com.theone.mvvm.ext.qmui.createItem
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
 * @date 2021/2/25 0025
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
class SampleFragment : BaseCoreFragment<BaseViewModel, FragmentSampleBinding>(),
    View.OnClickListener {

    lateinit var mGroupListView: QMUICommonListItemView
    lateinit var mPager: QMUICommonListItemView
    lateinit var mTest: QMUICommonListItemView
    lateinit var mStringExt: QMUICommonListItemView
    lateinit var mCrash: QMUICommonListItemView
    lateinit var mCustomView: QMUICommonListItemView
    lateinit var mLoader: QMUICommonListItemView

    override fun initView(root: View) {
        getTopBar()?.setTitleWithBackBtn("示例", this)
        getDataBinding().groupListView.run {
            mPager = createItem("BasePagerPullRefreshFragment")
            mGroupListView = createItem("QMUIGroupListView")
            mStringExt=  createItem("StringExt")
            mTest = createItem("Test")
            mCrash = createItem("崩溃测试")
            mCustomView =  createItem("CustomView")
            mLoader =  createItem("Loader")
            addToGroup(mPager, mGroupListView,mLoader, title = "ui", listener = this@SampleFragment)
            addToGroup(mStringExt,mCrash, title = "工具", listener = this@SampleFragment)
            addToGroup(mCustomView, title = "widget", listener = this@SampleFragment)
            addToGroup(mTest, title = "其他",listener = this@SampleFragment)
        }
    }

    override fun onLazyInit() {

    }

    override fun onClick(v: View?) {
        if(v == mCrash){
             arrayListOf<String>()[1]
            return
        }
        startFragment(
            when (v) {
                mPager -> SamplePagerFragment()
                mGroupListView -> GroupListViewFragment()
                mStringExt -> StringExtFragment()
                mCustomView -> CustomViewFragment()
                mLoader -> LoaderFragment()
                else -> {
//                    WebExplorerFragment.newInstance(
//                        BannerResponse(
//                            title = "",
//                            url = Url.INTEGRAL_RULES
//                        )
//                    )
                    TestFragment()
                }
            }
        )
    }

    override fun createObserver() {
    }

}