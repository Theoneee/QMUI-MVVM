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
                    "https://qdbh-ct01.baidupcs.com/file/74c3122c4t7e3cac6a08e05c7d9c9824?bkt=en-d3a65691252603d3977699d2819e1cd162c901d2c54797631a80877347e358cf48ff510c8853e754&fid=3180483061-250528-1093130434092558&time=1692582304&sign=FDTAXUbGERLQlBHSKfWqiu-DCb740ccc5511e5e8fedcff06b081203-wdZSwMCG2VdvSS6cQoympWNT9LM%3D&to=411&size=11349651&sta_dx=11349651&sta_cs=1178&sta_ft=mp3&sta_ct=7&sta_mt=7&fm2=MH%2CXian%2CAnywhere%2C%2C%E8%B4%B5%E5%B7%9E%2Cct&ctime=1609748400&mtime=1609748400&resv0=-1&resv1=0&resv2=rlim&resv3=5&resv4=11349651&vuk=1008605584&iv=0&htype=&randtype=&tkbind_id=0&newver=1&newfm=1&secfm=1&flow_ver=3&pkey=en-66306e9c5851c30475ddec61f12723b9ae2feff981b65e6fb9611f6ae27443374e60ecc44117ebd8&sl=76480590&expires=8h&rt=sh&r=835723520&vbdid=2634615794&fin=%E4%BF%A1%E4%B9%90%E5%9B%A2+-+%E7%A6%BB%E6%AD%8C.mp3&fn=%E4%BF%A1%E4%B9%90%E5%9B%A2+-+%E7%A6%BB%E6%AD%8C.mp3&rtype=1&clienttype=0&dp-logid=8848922631141341270&dp-callid=0.1&hps=1&tsl=80&csl=80&fsl=-1&csign=X6vhK4ap3IV6KWHoaIEsgTM1dsM%3D&so=0&ut=6&uter=4&serv=0&uc=32672573&ti=05df9239daa40647ade621ac09fc1d13ae5fedcb06a57a51305a5e1275657320&hflag=30&from_type=1&adg=c_1a5ba858e615c032fee5537331f23e0b&reqlabel=250528_f_ebc98dce94eb9425239b6d60fcc9744d_-1_bfcbcddd31ddfccf5799dfad007036f4&fpath=File%2F2020%2F17&by=themis&resvsflag=1-0-0-1-1-1",
                    "download_music_${System.currentTimeMillis()}.mp3"
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