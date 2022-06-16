package com.theone.mvvm.core.data.entity

import android.view.Gravity
import com.theone.mvvm.entity.QMUIItem
import com.theone.mvvm.ext.qmui.NO_SET


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
data class QMUIItemBean(var title:CharSequence, var normalRes:Int= NO_SET, var selectRes:Int = NO_SET,var gravity: Int = Gravity.CENTER):QMUIItem{

    override fun getItemTitle(): CharSequence  = title

    override fun getItemNormalImage(): Int  = normalRes

    override fun getItemSelectImage(): Int = selectRes

    override fun getItemGravity(): Int = gravity

}