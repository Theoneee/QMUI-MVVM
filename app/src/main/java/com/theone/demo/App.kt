package com.theone.demo

import android.app.Application
import com.theone.common.util.ThemeUtil
import com.theone.demo.ui.activity.ErrorActivity
import com.theone.demo.ui.activity.LauncherActivity
import com.theone.mvvm.core.app.CoreApplication
import com.theone.mvvm.core.app.ext.code
import com.theone.mvvm.core.data.entity.RxHttpBuilder
import com.theone.mvvm.core.app.ext.initCrashConfig
import com.theone.mvvm.core.app.util.RxHttpManager


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
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
class App : CoreApplication() {

    override fun isDebug(): Boolean = BuildConfig.DEBUG

    override fun init(application: Application) {
        initCrashConfig(LauncherActivity::class.java, ErrorActivity::class.java)
        super.init(application)
        ThemeUtil.init(application)
        RxHttpManager.run {
            init(RxHttpBuilder(isNeedCookie = true)).setDebug(DEBUG,true,2)
            initCustomExceptionParse {
                if (it.code == 502) {
                    "服务器正在升级,请稍等"
                } else {
                    null
                }
            }
        }
    }

}
