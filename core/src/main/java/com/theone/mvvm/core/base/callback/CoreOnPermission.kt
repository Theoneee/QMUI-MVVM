package com.theone.mvvm.core.base.callback

import android.content.Context
import com.hjq.permissions.OnPermission
import com.theone.mvvm.core.app.ext.showNoPermissionDialog

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
 * @date 2022-07-06 16:15
 * @describe 自定义权限申请返回
 * @email 625805189@qq.com
 * @remark
 */
abstract class CoreOnPermission (val context: Context) : OnPermission {

    override fun noPermission(denied: MutableList<String>?, quick: Boolean) {
        context.showNoPermissionDialog(quick, denied)
    }

}