package com.theone.mvvm.core.base.fragment

import android.view.View
import android.widget.RelativeLayout
import com.qmuiteam.qmui.kotlin.matchParent
import com.qmuiteam.qmui.kotlin.wrapContent
import com.qmuiteam.qmui.widget.QMUITopBarLayout
import com.qmuiteam.qmui.widget.QMUIViewPager
import com.qmuiteam.qmui.widget.tab.QMUITabSegment
import com.theone.common.ext.*
import com.theone.mvvm.base.viewmodel.BaseViewModel
import com.theone.mvvm.core.databinding.BaseTabInTitleLayoutBinding
import net.lucode.hackware.magicindicator.MagicIndicator


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
 * @date 2021/3/3 0003
 * @describe Tab在TitleBar类型
 * @email 625805189@qq.com
 * @remark
 */
abstract class BaseTabInTitleFragment<VM : BaseViewModel> :
    BaseTabFragment<VM, BaseTabInTitleLayoutBinding>() {

    private val mMagicIndicator: MagicIndicator by lazy {
        MagicIndicator(context).apply {
            layoutParams = generateMagicIndicatorLayoutParams()
        }
    }

    /**
     * TopBar两边可以增加返回按钮，搜索按钮之类的处理
     * 如：
     * addLeftBackImageButton()
     * addRightImageButton(R.drawable.mz_titlebar_ic_search_dark,R.id.topbar_right_view)
     */
    override fun QMUITopBarLayout.initTopBar() {
        setCenterView(getMagicIndicator())
    }

    /**
     * 生成[MagicIndicator]的[RelativeLayout.LayoutParams]
     * 也是为了TopBar两边如果增加其他内容时子类重写
     * 如：
     *
     *  override fun generateMagicIndicatorLayoutParams():RelativeLayout.LayoutParams{
     *      return super.generateMagicIndicatorLayoutParams().apply{
     *          addRule(RelativeLayout.RIGHT_OF,R.id.qmui_topbar_item_left_back)
     *          addRule(RelativeLayout.LEFT_OF,R.id.topbar_right_view)
     *      }
     *  }
     *
     * @return RelativeLayout.LayoutParams
     */
    protected open fun generateMagicIndicatorLayoutParams(): RelativeLayout.LayoutParams {
        return RelativeLayout.LayoutParams(wrapContent, matchParent)
    }

    /**
     * 当前界面需要创建[QMUITopBarLayout]
     * @return Boolean
     */
    override fun showTopBar(): Boolean = true

    override fun getDataBindingClass(): Class<BaseTabInTitleLayoutBinding> =
        BaseTabInTitleLayoutBinding::class.java

    override fun getMagicIndicator(): MagicIndicator = mMagicIndicator

    override fun getViewPager(): QMUIViewPager = getDataBinding().mQMUIViewPager

    override fun getTabSegment(): QMUITabSegment? = null

}