package com.theone.demo.ui.activity

import android.view.View
import com.theone.common.ext.setPrimaryClip
import com.theone.demo.app.ext.joinQQGroup
import com.theone.mvvm.core.ui.activity.TheErrorActivity
import com.theone.mvvm.ext.qmui.showMsgTipsDialog

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
 * @date 2021-03-31 15:21
 * @describe 崩溃后错误显示界面
 * @email 625805189@qq.com
 * @remark
 */
class ErrorActivity :
    TheErrorActivity() {

    override fun sendErrorMsg(view: View) {
        errorMsg.setPrimaryClip(this, "errorLog")
        showMsgTipsDialog("已复制错误日志") {
            joinQQGroup("26hK_GKmpQJbBHpfPIMlJztVmzTRyzZp")
        }
    }

}

