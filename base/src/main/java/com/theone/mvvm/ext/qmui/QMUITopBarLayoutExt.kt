package com.theone.mvvm.ext.qmui

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.qmuiteam.qmui.R
import com.qmuiteam.qmui.widget.QMUITopBarLayout
import com.theone.mvvm.base.activity.BaseQMUIActivity
import com.theone.mvvm.base.fragment.BaseQMUIFragment


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
 * @date 2021/2/22 0022
 * @describe QMUI相关组件的一些扩展函数
 * @email 625805189@qq.com
 * @remark
 */

fun QMUITopBarLayout.setTitleWithBackBtn(title: String?="", fragment: BaseQMUIFragment) {
    setTitle(title)
    addLeftCloseImageBtn(fragment)
}

fun QMUITopBarLayout.setTitleWithBackBtn(@StringRes resId: Int, fragment: BaseQMUIFragment) {
    setTitleWithBackBtn(context.getString(resId), fragment)
}

fun BaseQMUIFragment.setTitleWitchBackBtn(@StringRes resId: Int){
   setTitleWitchBackBtn(context?.getString(resId))
}

fun BaseQMUIFragment.setTitleWitchBackBtn(title: String?=""){
    getTopBar()?.setTitleWithBackBtn(title,this)
}

fun BaseQMUIFragment.addLeftCloseImageBtn( drawableResId: Int = NO_SET){
    getTopBar()?.addLeftCloseImageBtn(this,drawableResId)
}

fun QMUITopBarLayout.addLeftCloseImageBtn(fragment: BaseQMUIFragment, drawableResId: Int = NO_SET) {
    when (drawableResId) {
        NO_SET -> addLeftBackImageButton()
        else -> addLeftImageButton(drawableResId, R.id.qmui_topbar_item_left_back)
    }.setOnClickListener {
        fragment.finish()
    }
}
