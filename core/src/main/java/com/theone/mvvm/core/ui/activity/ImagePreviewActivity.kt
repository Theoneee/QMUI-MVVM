package com.theone.mvvm.core.ui.activity

import android.os.Bundle
import com.theone.common.constant.BundleConstant
import com.theone.mvvm.base.activity.BaseFragmentActivity
import com.theone.mvvm.core.R
import com.theone.mvvm.core.data.entity.ImagePreviewEvent
import com.theone.mvvm.core.ui.fragment.ImagePreviewFragment

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
 * @date 2021-04-25 10:36
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
class ImagePreviewActivity:BaseFragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        overridePendingTransition(R.anim.scale_enter, R.anim.slide_still)
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            val fragment = ImagePreviewFragment.newInstance(intent.getParcelableExtra<ImagePreviewEvent>(BundleConstant.DATA))
            supportFragmentManager
                .beginTransaction()
                .add(contextViewId, fragment, fragment.javaClass.simpleName)
                .addToBackStack(fragment.javaClass.simpleName)
                .commit()
        }
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.slide_still, R.anim.scale_exit)
    }

}