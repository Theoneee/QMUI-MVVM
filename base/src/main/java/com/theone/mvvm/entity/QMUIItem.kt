package com.theone.mvvm.entity

import android.view.Gravity
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
 * @date 2022-06-16 08:08
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */

interface QMUIItem {

    fun getItemTitle():CharSequence

    fun getItemNormalImage() = NO_SET

    fun getItemSelectImage() = NO_SET

    fun getItemGravity() = Gravity.CENTER

}