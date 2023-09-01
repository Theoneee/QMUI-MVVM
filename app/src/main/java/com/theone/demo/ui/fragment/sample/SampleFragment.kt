package com.theone.demo.ui.fragment.sample

import android.view.View
import com.qmuiteam.qmui.widget.QMUITopBarLayout
import com.qmuiteam.qmui.widget.grouplist.QMUICommonListItemView
import com.theone.demo.app.util.AppUpdateUtil
import com.theone.demo.databinding.FragmentSampleBinding
import com.theone.mvvm.base.fragment.BaseVbFragment
import com.theone.mvvm.ext.qmui.addToGroup
import com.theone.mvvm.base.viewmodel.BaseViewModel
import com.theone.mvvm.core.app.util.DownloadUtil
import com.theone.mvvm.core.base.fragment.BaseCoreFragment
import com.theone.mvvm.core.data.entity.ImagePreviewBean
import com.theone.mvvm.ext.qmui.createItem
import com.theone.mvvm.ext.qmui.setTitleWithBackBtn
import com.theone.mvvm.ext.qmui.showTips


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
class SampleFragment : BaseVbFragment<FragmentSampleBinding>(),
    View.OnClickListener {

    lateinit var mGroupListView: QMUICommonListItemView
    lateinit var mPager: QMUICommonListItemView
    lateinit var mTest: QMUICommonListItemView
    lateinit var mStringExt: QMUICommonListItemView
    lateinit var mCrash: QMUICommonListItemView
    lateinit var mCustomView: QMUICommonListItemView
    lateinit var mLoader: QMUICommonListItemView
    lateinit var mAppUpdate: QMUICommonListItemView
    lateinit var mPictureSelect: QMUICommonListItemView
    lateinit var mBottomSheet: QMUICommonListItemView
    lateinit var mDownload: QMUICommonListItemView

    override fun QMUITopBarLayout.initTopBar() {
        setTitleWithBackBtn("示例", this@SampleFragment)
    }

    override fun initView(root: View) {
        getViewBinding().groupListView.run {
            mPager = createItem("BasePagerPullRefreshFragment")
            mGroupListView = createItem("QMUIGroupListView")
            mStringExt = createItem("StringExt")
            mTest = createItem("Test")
            mCrash = createItem("崩溃测试")
            mCustomView = createItem("CustomView")
            mLoader = createItem("Loader")
            mAppUpdate = createItem("AppUpdate")
            mPictureSelect = createItem("PictureSelect")
            mBottomSheet = createItem("QMUIBottomSheet")
            mDownload = createItem("DownloadUtil")
            addToGroup(
                mPager,
                mGroupListView,
                mBottomSheet,
                mLoader,
                mPictureSelect,
                title = "ui",
                listener = this@SampleFragment
            )
            addToGroup(
                mAppUpdate,
                mStringExt,
                mDownload,
                mCrash,
                title = "工具",
                listener = this@SampleFragment
            )
            addToGroup(mCustomView, title = "widget", listener = this@SampleFragment)
            addToGroup(mTest, title = "其他", listener = this@SampleFragment)

            showTips(mDownload, isDot = true)
            showTips(mBottomSheet, isDot = false)
        }
    }

    override fun onClick(v: View?) {
        when (v) {
            mCrash -> {
                arrayListOf<String>()[1]
            }

            mAppUpdate -> {
                AppUpdateUtil(mActivity, true).checkUpdate()
            }

            mDownload -> {
                DownloadUtil.startDownload(
                    mActivity,
                    "http://xiaobubox.cn:11100/d/music/%E7%A6%BB%E6%AD%8C-%E4%BF%A1%E4%B9%90%E5%9B%A2-28665489.flac",
                    "信乐团-离歌_${System.currentTimeMillis()}.mp3"
                )
            }

            else -> startFragment(
                when (v) {
                    mPager -> SamplePagerFragment()
                    mGroupListView -> GroupListViewFragment()
                    mBottomSheet -> QMUIBottomSheetFragment()
                    mStringExt -> StringExtFragment()
                    mCustomView -> CustomViewFragment()
                    mLoader -> LoaderFragment()
                    mPictureSelect -> PictureSelectFragment()
                    else -> {
                        TestFragment()
//                        ScrollPositionFragment()
                    }
                }
            )
        }
    }

}