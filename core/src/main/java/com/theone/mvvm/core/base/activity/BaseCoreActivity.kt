package com.theone.mvvm.core.base.activity

import android.view.KeyEvent
import android.view.View
import androidx.databinding.ViewDataBinding
import com.hjq.toast.ToastUtils
import com.theone.mvvm.base.activity.BaseVmDbActivity
import com.theone.mvvm.base.viewmodel.BaseViewModel
import com.theone.mvvm.core.R
import com.theone.mvvm.core.base.callback.ICore
import com.theone.mvvm.core.app.ext.hideProgressDialog
import com.theone.mvvm.core.app.ext.registerLoader
import com.theone.mvvm.core.app.ext.showProgressDialog
import com.theone.mvvm.core.base.loader.LoaderService
import com.theone.mvvm.entity.ProgressBean

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
 * @date 2021-04-16 15:19
 * @describe BaseCoreActivity
 * @email 625805189@qq.com
 * @remark 添加界面状态管理
 */
abstract class BaseCoreActivity<VM : BaseViewModel, DB : ViewDataBinding>:BaseVmDbActivity<VM,DB>(),
    ICore {

    private var mLoader:LoaderService? = null

    override fun getLoader(): LoaderService?  = mLoader

    override fun setContentView(view: View?) {
        super.setContentView(view)
        mLoader = registerLoader()
    }

    override fun showProgress(progress: ProgressBean) {
        showProgressDialog(progress)
    }

    override fun hideProgress() {
        hideProgressDialog()
    }

    override fun showExitTips() {
        ToastUtils.show(R.string.core_exit_tips)
    }

    private var exitTime: Long = 0

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && isExitPage()) {
            if (System.currentTimeMillis() - exitTime > 2000) {
                exitTime = System.currentTimeMillis()
                showExitTips()
            } else {
                finish()
            }
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

}