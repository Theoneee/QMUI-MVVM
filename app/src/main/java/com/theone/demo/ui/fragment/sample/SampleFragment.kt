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
                    "https://v2.kwaicdn.com/upic/2023/07/31/21/BMjAyMzA3MzEyMTQ5NTlfMTA4ODIyMzE1Nl8xMDkzNjY3NTY1ODRfMF8z_b_Bce3f89b78761d54e5dc7cc9e7268504f.mp4?pkey=AAUIZbw6bOJMGNT8_V8BnzgNbuuu9Jm280EbwlRj97SCr9kHbpMJz08oHQIfT9RisX1Dq_Jn2pyUT9RN7k6BelyCKBG_p20SxEt-W1I5XKAS5WMrLPjRNRq47vUbgV69eOc&tag=1-1690877328-unknown-0-9cglwbql6b-1eb5b7134326391b&clientCacheKey=3xymeg8ugcddrcm_b.mp4&di=728bb44b&bp=14944&tt=b&ss=vp",
                    "download_video_${System.currentTimeMillis()}.mp4"
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
//                        TestFragment()
                        ScrollPositionFragment()
                    }
                }
            )
        }
    }

}