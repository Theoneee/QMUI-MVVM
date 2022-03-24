package com.theone.common.ext

import android.app.Activity
import androidx.fragment.app.Fragment

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
 * @date 2021-04-27 13:28
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */

inline fun <reified T:Any> Activity.getValue(label:String, defaultValue:T?=null) = lazy {
    val value = intent?.extras?.get(label)
    if(value is T) value else defaultValue
}

inline fun <reified T:Any> Activity.getValueNonNull(label:String, defaultValue:T?=null) = lazy {
    val value = intent?.extras?.get(label)
    requireNotNull(if(value is T) value else defaultValue){
        label
    }
}

inline fun <reified T:Any> Fragment.getValue(label:String, defaultValue:T?=null) = lazy {
    val value = arguments?.get(label)
    if(null != value && value is T) value else defaultValue
}

inline fun <reified T:Any> Fragment.getValueNonNull(label:String, defaultValue:T?=null) = lazy {
    val value = arguments?.get(label)
    requireNotNull(if(value is T) value else defaultValue){
        label
    }
}
