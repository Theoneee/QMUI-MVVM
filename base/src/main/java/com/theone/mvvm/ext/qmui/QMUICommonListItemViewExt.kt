package com.theone.mvvm.ext.qmui

import android.text.TextUtils
import android.view.View
import android.widget.CompoundButton
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import com.qmuiteam.qmui.widget.grouplist.QMUICommonListItemView
import com.qmuiteam.qmui.widget.grouplist.QMUICommonListItemView.*
import com.qmuiteam.qmui.widget.grouplist.QMUIGroupListView


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
 * @describe QMUIGroupListView
 * @email 625805189@qq.com
 * @remark
 */

val NO_SET = -1

fun QMUIGroupListView.createItem(
    @StringRes title: Int,
    @StringRes detail: Int = NO_SET,
    @DrawableRes drawable: Int = NO_SET,
    @QMUICommonListItemOrientation position: Int = HORIZONTAL,
    @QMUICommonListItemAccessoryType type: Int = ACCESSORY_TYPE_CHEVRON
): QMUICommonListItemView {
    val d = if (detail == NO_SET) null else context.getString(detail)
    return createItem(context.getString(title), d, drawable, position, type)
}

fun QMUIGroupListView.createItem(
    title: CharSequence,
    detail: CharSequence? = null,
    @DrawableRes drawable: Int = NO_SET,
    @QMUICommonListItemOrientation position: Int = HORIZONTAL,
    @QMUICommonListItemAccessoryType type: Int = ACCESSORY_TYPE_CHEVRON
): QMUICommonListItemView {
    val item = createItemView(title)
    return item.apply {
        if (!TextUtils.isEmpty(detail)) {
            detailText = detail
            orientation = position
        }
        accessoryType = type
        if (drawable != NO_SET) {
            setImageDrawable(ContextCompat.getDrawable(context, drawable))
        }
    }
}

fun QMUIGroupListView.createSwitchItem(
    @StringRes title: Int,
    @StringRes detail: Int = NO_SET,
    @DrawableRes drawable: Int = NO_SET,
    isCheck: Boolean = false,
    listener: CompoundButton.OnCheckedChangeListener?
): QMUICommonListItemView {
    val d = if (detail == NO_SET) null else context.getString(detail)
    return createSwitchItem(context.getString(title), d, drawable, isCheck, listener)
}

fun QMUIGroupListView.createSwitchItem(
    title: CharSequence,
    detail: CharSequence?="",
    @DrawableRes drawable: Int = NO_SET,
    isCheck: Boolean = false,
    listener: CompoundButton.OnCheckedChangeListener?
): QMUICommonListItemView {
    return createItem(
        title,
        detail,
        drawable,
        VERTICAL,
        ACCESSORY_TYPE_SWITCH
    ).apply {
        switch.isChecked = isCheck
        switch.setOnCheckedChangeListener(listener)
    }
}

fun QMUIGroupListView.createCustomViewItem(
    @StringRes title: Int,
    @StringRes detail: Int = NO_SET,
    @DrawableRes drawable: Int = NO_SET,
    view: View
): QMUICommonListItemView {
    val d = if (detail == NO_SET) null else context.getString(detail)
    return createCustomViewItem(context.getString(title), d, drawable, view)
}

fun QMUIGroupListView.createCustomViewItem(
    title: CharSequence,
    detail: CharSequence? = null,
    @DrawableRes drawable: Int = NO_SET,
    view: View
): QMUICommonListItemView {
    return createItem(
        title,
        detail,
        drawable,
        VERTICAL,
        ACCESSORY_TYPE_CUSTOM
    ).apply {
        addAccessoryCustomView(view)
    }
}

fun QMUICommonListItemView.removeIconTintColor() {
    val config = SkinConfig()
    config.iconTintColorRes = 0
    setSkinConfig(config)
}

fun showTips(
    vararg items: QMUICommonListItemView,
    showLeft: Boolean = false,
    isDot: Boolean = true
) {
    val p =
        if (showLeft) TIP_POSITION_LEFT else TIP_POSITION_RIGHT
    for (item in items) {
        item.setTipPosition(p)
        if (isDot)
            item.showRedDot(true)
        else
            item.showNewTip(true)
    }
}

/**
 *
 * @receiver QMUIGroupListView
 * @param items Array<out QMUICommonListItemView>
 * @param title CharSequence?  标题-如果不想要标题栏返回null,只需要一个分割线返回一个空字符串
 * @param description CharSequence? 描述-同上
 * @param listener OnClickListener? 点击监听
 */
fun QMUIGroupListView.addToGroup(
    vararg items: QMUICommonListItemView,
    title: CharSequence? = null,
    description: CharSequence? = null,
    listener: View.OnClickListener? = null
) {
    val section = QMUIGroupListView.newSection(context)
    if (null == title) {
        section.setUseDefaultTitleIfNone(false).setUseTitleViewForSectionSpace(false)
    } else {
        section.setTitle(title)
    }
    if (null != description) {
        section.setDescription(description)
    }
    for (item in items) {
        val li = if (null != item.switch) null else listener
        section.addItemView(item, li)
    }
    section.addTo(this)
}

