package com.theone.demo.ui.fragment.share

import android.view.View
import com.theone.demo.R
import com.theone.demo.ui.fragment.base.BaseArticleFragment
import com.theone.demo.viewmodel.ShareArticleViewModel
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
 * @date 2021/3/5 0005
 * @describe 我分享的文章
 * @email 625805189@qq.com
 * @remark
 */
class ShareArticleFragment:
    BaseArticleFragment<ShareArticleViewModel>() {

    override fun initView(rootView: View) {
        super.initView(rootView)
        getTopBar()?.run {
            setTitleWithBackBtn("我的分享",this@ShareArticleFragment)
            addRightImageButton(R.drawable.icon_add_dark,R.id.topbar_right_button1).setOnClickListener{
                startFragment(AddShareArticleFragment())
            }
        }
    }

    override fun createObserver() {
        super.createObserver()
        mAppVm.shareArticle.observe(this){
            onAutoRefresh()
        }
    }

}