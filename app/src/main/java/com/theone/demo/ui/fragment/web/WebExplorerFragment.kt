package com.theone.demo.ui.fragment.web


import android.text.TextUtils
import android.view.Gravity
import android.view.View
import android.widget.RelativeLayout
import com.qmuiteam.qmui.kotlin.matchParent
import com.qmuiteam.qmui.kotlin.wrapContent
import com.qmuiteam.qmui.widget.QMUIProgressBar
import com.qmuiteam.qmui.widget.QMUITopBarLayout
import com.qmuiteam.qmui.widget.webview.QMUIWebViewContainer
import com.theone.demo.databinding.FragmentWebExploererBinding
import com.theone.mvvm.base.viewmodel.BaseViewModel
import com.theone.common.constant.BundleConstant
import com.theone.common.ext.bundle
import com.theone.common.ext.dp2px
import com.theone.common.ext.toHtml
import com.theone.common.widget.TheMarqueeTextView
import com.theone.mvvm.core.R
import com.theone.mvvm.core.base.fragment.BaseWebFragment
import com.theone.mvvm.core.base.callback.IWeb
import com.theone.mvvm.ext.qmui.addLeftCloseImageBtn


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
 * @date 2021/3/8 0008
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
class WebExplorerFragment private constructor() :
    BaseWebFragment<BaseViewModel, FragmentWebExploererBinding>() {

    companion object {
        fun newInstance(data: IWeb) = WebExplorerFragment().bundle {
            putParcelable(BundleConstant.DATA, data)
        }
    }

    private lateinit var mTitleView: TheMarqueeTextView

    override fun getWebContainer(): QMUIWebViewContainer = getDataBinding().mWebContainer

    override fun getProgressBar(): QMUIProgressBar = getDataBinding().progressbar

    override fun getTopBar(): QMUITopBarLayout = getDataBinding().topbar

    override fun QMUITopBarLayout.initTopBar() {
        addLeftCloseImageBtn(this@WebExplorerFragment)
        // QMUI的Title用的是QMUIQQFaceView，无法使用跑马灯效果，这里重新设置一个
        mTitleView = TheMarqueeTextView(context).apply {
            layoutParams = RelativeLayout.LayoutParams(matchParent, wrapContent).apply {
                addRule(RelativeLayout.RIGHT_OF, R.id.qmui_topbar_item_left_back)
                gravity = Gravity.START
                marginEnd = dp2px(20)
            }
            marqueeRepeatLimit = Int.MAX_VALUE
            isFocusable = true
            textSize = 17f
            ellipsize = TextUtils.TruncateAt.MARQUEE
            isSingleLine = true
            setHorizontallyScrolling(true)
            isFocusableInTouchMode = true
            mIWeb.getWebTitle()?.let {
                text = it.toHtml()
            }
        }
        setCenterView(mTitleView)
    }

    override fun setTopBarTitle(title: String?) {
        mTitleView.text = title
    }

}