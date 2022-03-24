package com.theone.mvvm.core.app.ext.qmui

import android.content.Context
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.ViewPager
import com.qmuiteam.qmui.widget.tab.QMUITab
import com.qmuiteam.qmui.widget.tab.QMUITabBuilder
import com.qmuiteam.qmui.widget.tab.QMUITabSegment
import com.theone.common.ext.getDrawable
import com.theone.mvvm.core.data.entity.QMUITabBean


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

fun QMUITabBuilder.createTab(
    context: Context,
    text: CharSequence,
    normal: Int = NO_SET,
    select: Int = NO_SET
): QMUITab {
    setText(text)
    if (normal != NO_SET)
        setNormalDrawable(getDrawable(context, normal))
    if (select != NO_SET)
        setSelectedDrawable(getDrawable(context, select))
    return build(context)
}

fun QMUITabBuilder.createTab(context: Context, tab: QMUITabBean): QMUITab {
    return createTab(context, tab.title, tab.normal, tab.select)
}

fun QMUITabSegment.init(viewPager: ViewPager, tabs: List<QMUITabBean>, builder: QMUITabBuilder) {
    for (tab in tabs) {
        addTab(builder.createTab(context, tab))
    }
    setupWithViewPager(viewPager, false)
}

fun MutableList<QMUITabBean>.addTab(title: String, normal: Int = NO_SET, select: Int = NO_SET) {
    add(QMUITabBean(title, normal,if(select == NO_SET) normal else select))
}

private val NO_SET: Int = -1