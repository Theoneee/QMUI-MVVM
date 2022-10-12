package com.theone.demo.ui.fragment.login

import android.view.View
import androidx.fragment.app.viewModels
import androidx.work.*
import com.theone.common.constant.BundleConstant.TYPE
import com.theone.common.ext.bundle
import com.theone.common.ext.getValueNonNull
import com.theone.demo.R
import com.theone.demo.app.util.CacheUtil
import com.theone.demo.data.model.bean.UserInfo
import com.theone.demo.viewmodel.LoginRegisterViewModel
import com.theone.demo.databinding.FragmentLoginRegisterBinding
import com.theone.demo.domain.work.LoginSignWorker
import com.theone.demo.viewmodel.AppViewModel
import com.theone.mvvm.core.app.ext.appViewModels
import com.theone.mvvm.core.base.fragment.BaseCoreFragment
import com.theone.mvvm.ext.qmui.showFailTipsDialog
import com.theone.mvvm.ext.qmui.showSuccessTipsExitDialog
import java.util.concurrent.TimeUnit

class LoginRegisterItemFragment private constructor() :
    BaseCoreFragment<LoginRegisterViewModel, FragmentLoginRegisterBinding>() {

    companion object {
        fun newInstant(isRegister: Boolean) = LoginRegisterItemFragment().bundle {
            putBoolean(TYPE, isRegister)
        }
    }

    private val mAppVm: AppViewModel by appViewModels()

    private val isRegister: Boolean by getValueNonNull(TYPE)

    override fun getBindingClick(): Any = ProxyClick()

    override fun getRootBackgroundColor(): Int = R.color.white

    override fun initView(root: View) {
        getViewModel().isRegister.set(isRegister)
    }

    /**
     * 开启每日登录签到任务
     * @receiver User
     */
    private fun UserInfo.doWorker() {
        // 添加约束，网络连接时才启用
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)    // 网络状态
            .setRequiresBatteryNotLow(false)                  // 不在电量不足时执行
            .setRequiresCharging(false)                       // 在充电时执行
            .setRequiresStorageNotLow(false)                  // 不在存储容量不足时执行
            //.setRequiresDeviceIdle(true)                    // 在待机状态下执行，需要 API 23
            .build()

        val inputData = Data.Builder().putString(LoginSignWorker.ACCOUNT, username)
            .putString(LoginSignWorker.PASSWORD, password).build()

        // 周期任务  一天执行一次签到
        val signRequest = PeriodicWorkRequest
            .Builder(LoginSignWorker::class.java, 1, TimeUnit.DAYS)
            .setConstraints(constraints)
            .setInputData(inputData)
            .addTag(LoginSignWorker.TAG)
            .build()

        WorkManager.getInstance(mActivity).enqueue(signRequest)
    }

    override fun createObserver() {
        getViewModel().run {
            getResponseLiveData().observe(this@LoginRegisterItemFragment) {
                mAppVm.userInfo.value = it
                CacheUtil.setUser(it)
                it.doWorker()
                showSuccessTipsExitDialog(if (isRegister.get()) "注册" else "登录" + "成功")
            }
            getErrorLiveData().observe(this@LoginRegisterItemFragment) {
                showFailTipsDialog(it)
            }
        }
    }

    inner class ProxyClick {

        fun login() {
            with(getViewModel()) {
                when {
                    account.get().isEmpty() -> showFailTipsDialog("请填写账号")
                    password.get().isEmpty() -> showFailTipsDialog("请填写密码")
                    isRegister.get() && repassword.get().isEmpty() -> showFailTipsDialog("请填写确认密码")
                    else -> {
                        requestServer()
                    }
                }
            }
        }
    }

}