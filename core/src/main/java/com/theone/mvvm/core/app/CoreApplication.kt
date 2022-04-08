package com.theone.mvvm.core.app

import android.app.Application
import com.hjq.toast.ToastUtils
import com.theone.common.ext.LogInit
import com.theone.mvvm.base.BaseApplication
import com.theone.mvvm.core.app.ext.initLoaderDefault
import com.theone.mvvm.core.app.util.MMKVUtil
import com.theone.mvvm.core.app.util.NotificationManager

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
 * @date 2021/3/23 0022
 * @describe 对Core里面的组件进行初始化
 * @email 625805189@qq.com
 * @remark
 */
abstract class CoreApplication : BaseApplication() {

    override fun init(application: Application) {
        MMKVUtil.init(application)
        LogInit(DEBUG)
        NotificationManager.getInstance().register()
        ToastUtils.init(this)
        initLoaderDefault()
    }

}