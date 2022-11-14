package com.theone.mvvm.core.ui.activity

import android.view.View
import cat.ereza.customactivityoncrash.CustomActivityOnCrash
import cat.ereza.customactivityoncrash.config.CaocConfig
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction
import com.theone.common.ext.setPrimaryClip
import com.theone.mvvm.base.activity.BaseQMUIActivity
import com.theone.mvvm.core.R
import com.theone.mvvm.entity.ProgressBean
import com.theone.mvvm.ext.qmui.showMsgDialog
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
open class TheErrorActivity :
    BaseQMUIActivity() {

    protected open val config: CaocConfig? by lazy {
        CustomActivityOnCrash.getConfigFromIntent(intent)
    }

    protected open val errorMsg: String by lazy {
        CustomActivityOnCrash.getAllErrorDetailsFromIntent(this, intent)
    }

    override fun getLayoutId(): Int = R.layout.activity_error

    override fun showProgressDialog(progress: ProgressBean) {
    }

    override fun hideProgressDialog() {
    }

    override fun initView(root: View) {
        getTopBar()?.run {
            setTitle(R.string.crash_title)
            updateBottomDivider(0, 0, 0, 0)
        }
    }

    open fun showErrorMsg(view: View) {
        showMsgDialog(
            "错误信息",
            errorMsg,
            leftAction = null,
            rightAction = "关闭",
            listener = QMUIDialogAction.ActionListener { dialog, index ->
                dialog.dismiss()
            },
            prop = QMUIDialogAction.ACTION_PROP_NEGATIVE
        )
    }

    open fun sendErrorMsg(view: View) {
        errorMsg.setPrimaryClip(this, "errorLog")
        showMsgTipsDialog("已复制错误日志")
    }

    open fun restart(view: View) {
        config?.let {
            CustomActivityOnCrash.restartApplication(this@TheErrorActivity, it)
        }
    }

}