package com.theone.demo.app

import android.app.Application
import com.theone.common.util.ThemeUtil
import com.theone.demo.BuildConfig
import com.theone.demo.ui.activity.ErrorActivity
import com.theone.demo.ui.activity.LauncherActivity
import com.theone.mvvm.core.app.CoreApplication
import com.theone.mvvm.core.data.entity.RxHttpBuilder
import com.theone.mvvm.core.app.ext.initCrashConfig
import com.theone.mvvm.core.app.util.RxHttpManager
import com.theone.mvvm.core.base.loader.Loader
import com.theone.mvvm.core.base.loader.callback.ErrorCallback
import com.theone.mvvm.core.base.loader.callback.LoadingCallback


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
        RxHttpManager.init(RxHttpBuilder(isNeedCookie = true)).setDebug(DEBUG)

        Loader.beginBuilder()
            .addCallback(LoadingCallback::class.java)
            .addCallback(ErrorCallback::class.java)
            .commit()
//        RxHttpManager.init(RxHttpBuilder(isNeedCookie = true,cookiejar = MyCookeJar(appContext))).setDebug(DEBUG)
    }
}
