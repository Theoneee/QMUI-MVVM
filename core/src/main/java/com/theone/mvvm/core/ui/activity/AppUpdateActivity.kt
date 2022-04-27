package com.theone.mvvm.core.ui.activity

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.view.View
import com.hjq.permissions.OnPermission
import com.hjq.permissions.Permission
import com.hjq.permissions.XXPermissions
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction
import com.theone.common.callback.OnKeyBackClickListener
import com.theone.common.constant.BundleConstant
import com.theone.common.ext.getAppName
import com.theone.common.ext.getValueNonNull
import com.theone.common.ext.installApk
import com.theone.common.widget.ProgressButton
import com.theone.mvvm.core.R
import com.theone.mvvm.core.base.activity.BaseCoreActivity
import com.theone.mvvm.core.base.callback.IApkUpdate
import com.theone.mvvm.core.data.entity.DownloadBean
import com.theone.mvvm.core.databinding.ActivityAppUpdateBinding
import com.theone.mvvm.core.service.DownloadService
import com.theone.mvvm.core.service.startDownloadService
import com.theone.mvvm.core.app.util.FileDirectoryManager
import com.theone.mvvm.core.ui.viewmodel.AppUpdateViewModel
import com.theone.mvvm.ext.qmui.showMsgDialog
import java.io.File

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
 * @date 2021-05-08 15:07
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */

class AppUpdateActivity : BaseCoreActivity<AppUpdateViewModel, ActivityAppUpdateBinding>() {

    private val mUpdate: IApkUpdate by getValueNonNull(BundleConstant.DATA)
    private val mFilter: IntentFilter by lazy {
        IntentFilter().apply {
            addAction(DownloadService.DOWNLOAD_OK)
            addAction(DownloadService.DOWNLOAD_ERROR)
            addAction(DownloadService.DOWNLOAD_ERROR_MSG)
            addAction(DownloadService.DOWNLOAD_PROGRESS)
            addAction(DownloadService.DOWNLOAD_PROGRESS_PERCENT)
        }
    }

    private val STATUS_START = "立即更新"
    private val STATUS_DOWNING = "下载中"
    private val STATUS_INSTALL = "立即安装"
    private val STATUS_RE_DOWNLOAD = "重新下载"

    override fun showTopBar(): Boolean = false

    override fun getRootBackgroundColor(): Int = R.color.qmui_config_color_transparent

    override fun initView(root: View) {
        overridePendingTransition(0, 0)
        getDataBinding().tvUpdate.run {
            buttonRadius = (height / 2).toFloat()
        }
    }

    override fun initData() {
        getViewModel().run {
            updateInfo.set(mUpdate.getAppUpdateLog())
            verCode.set("Ver ${mUpdate.getAppVersionName()}")
            isForce.set(mUpdate.isForceUpdate())
        }
    }

    override fun getBindingClick(): Any = ClickProxy()

    inner class ClickProxy {

        fun download() {
            if (getDataBinding().tvUpdate.currentText.isNotEmpty()) {
                when (getDataBinding().tvUpdate.currentText) {
                    STATUS_INSTALL -> {
                        installApk(checkUpdateApkFile())
                    }
                    STATUS_DOWNING -> {

                    }
                    else -> {
                        getDataBinding().tvUpdate.setProgressText(STATUS_DOWNING, 0.0f)
                        requestPermission()
                    }
                }
            }
        }

        fun close() {
            finish()
        }
    }

    override fun createObserver() {

    }

    private fun requestPermission() {
        XXPermissions.with(this).run {
            if(applicationInfo.targetSdkVersion >=30){
                permission(Permission.MANAGE_EXTERNAL_STORAGE)
            }else{
                permission(Permission.WRITE_EXTERNAL_STORAGE)
            }
        }
            .constantRequest()
            .request(object : OnPermission {

                override fun hasPermission(granted: MutableList<String>?, all: Boolean) {
                    if (all) {
                        doDownload()
                    }
                }

                override fun noPermission(denied: MutableList<String>?, quick: Boolean) {
                    if (quick) {
                        showMsgDialog(
                            "提示",
                            "权限被禁止，请在设置里打开权限",
                            listener = QMUIDialogAction.ActionListener { dialog, index ->
                                dialog.dismiss()
                                XXPermissions.startPermissionActivity(
                                    this@AppUpdateActivity,
                                    denied
                                )
                            },
                            prop = QMUIDialogAction.ACTION_PROP_NEGATIVE
                        ).run {
                            setCanceledOnTouchOutside(false)
                            setOnKeyListener(OnKeyBackClickListener())
                        }
                    }
                }

            })
    }

    private fun doDownload() {
        DownloadBean(
            mUpdate.getAppApkUrl(),
            getApkDownloadPath(),
            getApkDownloadName()
        ).let {
            startDownloadService(it)
        }
        getDataBinding().tvUpdate.state = ProgressButton.DOWNLOADING
    }

    private fun getApkDownloadPath(): String = FileDirectoryManager.getUpdateAPKDownloadPath()

    private fun getApkDownloadName(): String {
        return getAppName() + "_" + mUpdate.getAppVersionName() + ".apk"
    }

    var mReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        @SuppressLint("SetTextI18n")
        override fun onReceive(context: Context, intent: Intent) {
            when (intent.action) {
                DownloadService.DOWNLOAD_OK -> {
                    // 下载完成
                    getDataBinding().tvUpdate.setCurrentText(STATUS_INSTALL)
                }
                DownloadService.DOWNLOAD_ERROR -> {
                    // 下载失败
                    val error =
                        intent.getStringExtra(DownloadService.DOWNLOAD_ERROR_MSG)
                    getDataBinding().tvUpdate.setCurrentText(STATUS_RE_DOWNLOAD)
                }
                else -> {
                    // 更新进度
                    val percent =
                        intent.getIntExtra(DownloadService.DOWNLOAD_PROGRESS_PERCENT, 0)
                    getDataBinding().tvUpdate.setProgressText(STATUS_DOWNING, percent * 1.0f)
                }
            }
        }
    }

    private fun checkUpdateApkFile(): File? {
        getDataBinding().tvUpdate.state = ProgressButton.NORMAL
        getDataBinding().tvUpdate.setCurrentText(STATUS_START)
        val file = File(getApkDownloadPath(), getApkDownloadName())
        if (file.exists()) {
            // 判断服务器APK大小，可能没有返回大小
            if (file.length() > 0) {
                mUpdate.getAppApkSize().let {
                    if (it > 0 && it != file.length()) {
                        file.delete()
                        return null
                    }
                }
                // 当前安装包已经存在，直接点击安装
                getDataBinding().tvUpdate.setCurrentText(STATUS_INSTALL)
                return file
            } else {
                file.delete()
            }
        }
        return null
    }


    override fun doOnBackPressed() {
        if (!mUpdate.isForceUpdate())
            super.doOnBackPressed()
    }

    override fun onResume() {
        super.onResume()
        registerReceiver(mReceiver, mFilter)
        checkUpdateApkFile()
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(mReceiver)
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(0, 0)
    }

}