package com.theone.mvvm.core.app.ext

import android.content.Context
import android.util.TypedValue
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import androidx.core.content.ContextCompat.getColor
import androidx.viewpager.widget.ViewPager
import com.qmuiteam.qmui.skin.QMUISkinValueBuilder
import com.qmuiteam.qmui.skin.defaultAttr.QMUISkinSimpleDefaultAttrProvider
import com.qmuiteam.qmui.util.QMUIResHelper
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton
import com.theone.common.ext.dp2px
import com.theone.common.ext.goneViews
import com.theone.mvvm.core.R
import com.theone.mvvm.core.data.entity.QMUIItemBean
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.WrapPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.badge.BadgePagerTitleView


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
 * @date 2021/3/2 0002
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */

fun IPagerTitleView.initBadgePager(
    context: Context
): IPagerTitleView {
    val badgePagerTitleView = BadgePagerTitleView(context)
    val badgeButton = QMUIRoundButton(context, null, R.attr.qmui_tab_sign_count_view)
    val skinProvider = QMUISkinSimpleDefaultAttrProvider()
    skinProvider.setDefaultSkinAttr(
        QMUISkinValueBuilder.BACKGROUND, R.attr.qmui_skin_support_tab_sign_count_view_bg_color
    )
    skinProvider.setDefaultSkinAttr(
        QMUISkinValueBuilder.TEXT_COLOR, R.attr.qmui_skin_support_tab_sign_count_view_text_color
    )
    badgeButton.setTag(R.id.qmui_skin_default_attr_provider, skinProvider)
    goneViews(badgeButton)

    badgePagerTitleView.run {
        innerPagerTitleView = this@initBadgePager
        badgeView = badgeButton
        isAutoCancelBadge = false
    }
    return badgePagerTitleView
}

fun SimplePagerTitleView.init(
    index: Int,
    tabs: List<QMUIItemBean>,
    viewPager: ViewPager
): IPagerTitleView {
    text = tabs[index].title
    setTextSize(
        TypedValue.COMPLEX_UNIT_PX,
        QMUIResHelper.getAttrDimen(context, R.attr.app_skin_tab_indicator_text_size).toFloat()
    )
    setOnClickListener { viewPager.currentItem = index }
    return this
}

fun LinePagerIndicator.init(): IPagerIndicator {
    mode = LinePagerIndicator.MODE_EXACTLY
    lineHeight = dp2px(3).toFloat()
    lineWidth = dp2px(15).toFloat()
    roundRadius = dp2px(2).toFloat()
    startInterpolator = AccelerateInterpolator()
    endInterpolator = DecelerateInterpolator(2.0f)
    return this
}

fun WrapPagerIndicator.init(fillColor: Int): IPagerIndicator {
    this.fillColor = getColor(context, fillColor)
    return this
}
