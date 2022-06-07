package com.theone.demo.ui.fragment.mine

import android.view.View
import com.qmuiteam.qmui.widget.grouplist.QMUICommonListItemView
import com.theone.common.ext.notNull
import com.theone.demo.R
import com.theone.demo.app.ext.joinQQGroup
import com.theone.demo.app.net.Url
import com.theone.demo.app.ext.checkLogin
import com.theone.demo.app.util.CacheUtil
import com.theone.demo.data.model.bean.BannerResponse
import com.theone.demo.data.model.bean.IntegralResponse
import com.theone.demo.data.model.bean.UserInfo
import com.theone.demo.databinding.FragmentMineBinding
import com.theone.demo.ui.fragment.sample.SampleFragment
import com.theone.demo.ui.fragment.setting.SettingFragment
import com.theone.demo.ui.fragment.web.WebExplorerFragment
import com.theone.demo.ui.fragment.collection.CollectionFragment
import com.theone.demo.ui.fragment.integral.IntegralHistoryFragment
import com.theone.demo.ui.fragment.integral.IntegralRankFragment
import com.theone.demo.ui.fragment.share.ShareArticleFragment
import com.theone.demo.viewmodel.AppViewModel
import com.theone.demo.viewmodel.state.MineViewModel
import com.theone.mvvm.core.base.fragment.BaseCoreFragment
import com.theone.mvvm.ext.getAppViewModel
import com.theone.mvvm.ext.qmui.*


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
class MineFragment : BaseCoreFragment<MineViewModel, FragmentMineBinding>(), View.OnClickListener {

    private val appVm: AppViewModel by lazy { getAppViewModel<AppViewModel>() }

    //private val mRequestVm: MineRequestViewModel by viewModels()

    private lateinit var mShare: QMUICommonListItemView
    private lateinit var mCollection: QMUICommonListItemView
    private lateinit var mSetting: QMUICommonListItemView
    private lateinit var mAPI: QMUICommonListItemView
    private lateinit var mJoinUs: QMUICommonListItemView
    private lateinit var mSample: QMUICommonListItemView

    override fun isNeedChangeStatusBarMode(): Boolean = true

    override fun initView(root: View) {
        getDataBinding().groupListView.run {
            mCollection = createItem("我的收藏", drawable = R.drawable.svg_mine_collection)
            mShare = createItem("我的分享", drawable = R.drawable.svg_mine_share)

            mAPI = createItem("开源网站", "玩Android", R.drawable.svg_mine_web)
            mJoinUs = createItem("加入我们", "QQ群：761201022", R.drawable.svg_mine_qq)
            mSetting = createItem("系统设置", "", R.drawable.svg_mine_setting)

            mSample = createItem("一些示例", drawable = R.drawable.svg_mine_sample)

            showTips(mSample)

            addToGroup(mCollection, mShare, listener = this@MineFragment)
            addToGroup(mAPI, mJoinUs, mSample, title = "", listener = this@MineFragment)
            addToGroup(mSetting, title = "", listener = this@MineFragment)

        }

        getDataBinding().swipeRefresh.setOnRefreshListener {
            requestIntegral()
        }
        setUserInfo(appVm.userInfo.value)
    }

    override fun onLazyInit() {
        appVm.userInfo.value?.run {
            requestIntegral()
        }
    }

    override fun createObserver() {
        getViewModel().mRequest.run {
            getResponseLiveData().observe(this@MineFragment) {
                setUserIntegral(it)
            }
            getErrorLiveData().observe(this@MineFragment) {
                if(it.contains("请先登录")){
                    appVm.userInfo.value = null
                    CacheUtil.setUser(null)
                }
                showFailTipsDialog(it)
            }
            getStateLiveData().observe(this@MineFragment) {
                if(!it){
                    getDataBinding().swipeRefresh.run {
                        isRefreshing = false
                        isEnabled = true
                    }
                    getViewModel().mRequest.isFirst = false
                }
            }
        }
        appVm.userInfo.observe(this) {
            setUserInfo(it)
        }
    }

    private fun requestIntegral() {
        getViewModel().run {
            getDataBinding().swipeRefresh.isRefreshing = !mRequest.isFirst
            requestData()
        }
    }

    private fun setUserIntegral(data: IntegralResponse) {
        getViewModel().run {
            integral.set("积分${data.coinCount}")
            rank.set("排名${data.rank}")
            level.set("等级${data.level}")
        }
    }

    private fun setUserInfo(it: UserInfo?) {
        getDataBinding().swipeRefresh.isEnabled = false
        it.notNull({
            requestIntegral()
            getViewModel().run {
                name.set(it.getUserName())
                id.set("ID " + it.id)
                if (it.icon.isNotEmpty())
                    imageUrl.set(it.icon)
            }
        }, {
            getViewModel().run {
                mRequest.isFirst = true
                name.set("请先登录~")
                id.set("ID")
                integral.set("积分")
                rank.set("排名")
                level.set("等级")
            }
        })
    }

    override fun onClick(p0: View?) {
        when (p0) {
            mShare -> checkLogin {
                startFragment(ShareArticleFragment())
            }
            mCollection -> checkLogin {
                startFragment(CollectionFragment())
            }
            mAPI -> startFragment(
                WebExplorerFragment.newInstance(
                    BannerResponse(
                        title = "玩Android",
                        url = Url.BASE_URL
                    )
                )
            )
            mJoinUs -> {
                context?.joinQQGroup("26hK_GKmpQJbBHpfPIMlJztVmzTRyzZp")
            }
            mSetting -> {
                startFragment(SettingFragment())
            }
            mSample -> {
                startFragment(SampleFragment())
            }
        }
    }

    override fun getBindingClick(): Any = ProxyClick()

    inner class ProxyClick {

        fun doLogin() {
            checkLogin {

            }
        }

        /**
         * 积分历史
         */
        fun integralHistory() {
            checkLogin {
                startFragment(IntegralHistoryFragment())
            }
        }

        /**
         * 积分排行
         */
        fun integralRank() {
            checkLogin {
                getViewModel().mRequest.getResponseLiveData().value?.let {
                    startFragment(IntegralRankFragment.newInstance(it))
                }
            }
        }

    }

}