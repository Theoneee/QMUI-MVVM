package com.theone.mvvm.core.ui.activity

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.view.KeyEvent
import android.view.View
import com.theone.common.constant.BundleConstant
import com.theone.common.ext.delay
import com.theone.common.ext.getAppName
import com.theone.common.ext.getValueNonNull
import com.theone.common.ext.installApk
import com.theone.common.widget.ProgressButton
import com.theone.mvvm.core.R
import com.theone.mvvm.core.app.ext.isServiceRunning
import com.theone.mvvm.core.base.activity.BaseCoreActivity
import com.theone.mvvm.core.base.callback.IApkUpdate
import com.theone.mvvm.core.data.entity.DownloadBean
import com.theone.mvvm.core.databinding.ActivityAppUpdateBinding
import com.theone.mvvm.core.service.DownloadService
import com.theone.mvvm.core.service.startDownloadService
import com.theone.mvvm.core.ui.viewmodel.AppUpdateViewModel
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

    private val mDownloadPath: String by lazy {
        (externalCacheDir ?: filesDir).absolutePath
    }

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
            verCode.set(mUpdate.getAppVersionName())
            isForce.set(mUpdate.isForceUpdate())
        }
    }

    override fun getBindingClick(): Any = ClickProxy()

    inner class ClickProxy {

        fun download() {
            when (getDataBinding().tvUpdate.state) {
                ProgressButton.INSTALLING -> {
                    installApk(checkUpdateApkFile())
                }
                ProgressButton.NORMAL -> {
                    getDataBinding().tvUpdate.setProgressText(STATUS_DOWNING, 0.0f)
                    doDownload()
                }
            }
        }

        fun close() {
            finish()
        }
    }

    override fun createObserver() {

    }

    private fun doDownload() {
        getDataBinding().tvUpdate.state = ProgressButton.DOWNLOADING
        DownloadBean(
            mUpdate.getAppApkUrl(),
            mDownloadPath,
            getApkDownloadName(),
            true
        ).let {
            startDownloadService(it)
        }
    }

    private fun getApkDownloadName(): String {
        return getAppName() + "_" + mUpdate.getAppVersionName() + ".apk"
    }

    private val mReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        @SuppressLint("SetTextI18n")
        override fun onReceive(context: Context, intent: Intent) {
            when (intent.action) {
                DownloadService.DOWNLOAD_OK -> {
                    // 下载完成
                    getDataBinding().tvUpdate.setCurrentText(STATUS_INSTALL)
                    getDataBinding().tvUpdate.state = ProgressButton.INSTALLING
                }
                DownloadService.DOWNLOAD_ERROR -> {
                    // 下载失败
                    getDataBinding().tvUpdate.setCurrentText(STATUS_RE_DOWNLOAD)
                    getDataBinding().tvUpdate.state = ProgressButton.NORMAL
                }
                else -> {
                    // 更新进度
                    getDataBinding().tvUpdate.state = ProgressButton.DOWNLOADING
                    val percent = intent.getIntExtra(DownloadService.DOWNLOAD_PROGRESS_PERCENT, 0)
                    getDataBinding().tvUpdate.setProgressText(STATUS_DOWNING, percent * 1.0f)
                }
            }
        }
    }

    private fun checkUpdateApkFile(): File? {
        val file = File(mDownloadPath, getApkDownloadName())
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
                getDataBinding().tvUpdate.state = ProgressButton.INSTALLING
                return file
            } else {
                file.delete()
            }
        }
        getDataBinding().tvUpdate.state = ProgressButton.NORMAL
        getDataBinding().tvUpdate.setCurrentText(STATUS_START)
        return null
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && mUpdate.isForceUpdate()) {
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

    override fun onResume() {
        super.onResume()
        registerReceiver(mReceiver, mFilter)
        delay(100) {
            if (getDataBinding().tvUpdate.state == ProgressButton.NORMAL) {
                checkUpdateApkFile()
            }
        }
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