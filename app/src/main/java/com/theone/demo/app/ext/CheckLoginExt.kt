package com.theone.demo.app.ext

import com.qmuiteam.qmui.arch.QMUIFragment
import com.theone.demo.app.util.CacheUtil
import com.theone.demo.ui.fragment.login.LoginRegisterFragment


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
 * @date 2021/3/5 0005
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
fun QMUIFragment.checkLogin(isLoginAction: () -> Unit = {}) {
    if (CacheUtil.isLogin()) {
        isLoginAction.invoke()
    } else {
        startFragment(LoginRegisterFragment())
    }
}