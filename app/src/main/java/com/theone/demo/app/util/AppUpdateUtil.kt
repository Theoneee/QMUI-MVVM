package com.theone.demo.app.util

import androidx.appcompat.app.AppCompatActivity
import com.theone.common.ext.delay
import com.theone.demo.data.model.bean.AppUpdate
import com.theone.mvvm.core.app.util.BaseAppUpdateUtil

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
 * @date 2022-04-14 09:14
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
class AppUpdateUtil(context: AppCompatActivity, show:Boolean): BaseAppUpdateUtil<AppUpdate>(context,show){

    override fun requestServe() {
        delay(1000){
            AppUpdate().onComplete()
        }
    }

}