package com.theone.common.ext

import java.util.*

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
 * @date 2021-04-09 09:12
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */

/**
 * 规范化价格字符串显示的工具类
 * @return 保留两位小数的价格字符串
 */
fun Double.regularizePrice():String{
    return String.format(Locale.CHINESE,"%.2f",this)
}

/**
 * 规范化价格字符串显示的工具类
 * @return 保留两位小数的价格字符串
 */
fun Float.regularizePrice():String{
    return String.format(Locale.CHINESE,"%.2f",this)
}