package com.theone.mvvm.entity

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
 * @date 2021-04-30 16:42
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */

/**
 *
 * @property msg String?
 * @property percent Int
 * @property max Int
 * @property outSideCancel Boolean
 * @property keyBackCancel Boolean  返回键是否能关闭
 * @constructor
 */
data class ProgressBean(val msg:String?= "", val percent:Int = 0, val max:Int = 100,val outSideCancel:Boolean = false, val keyBackCancel:Boolean = false) {
}