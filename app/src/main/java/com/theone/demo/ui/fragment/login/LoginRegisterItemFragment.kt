package com.theone.demo.ui.fragment.login

import android.view.View
import androidx.lifecycle.Observer
import com.theone.common.constant.BundleConstant.TYPE
import com.theone.common.ext.bundle
import com.theone.common.ext.getColor
import com.theone.common.ext.getValueNonNull
import com.theone.demo.R
import com.theone.demo.app.util.CacheUtil
import com.theone.demo.viewmodel.LoginRegisterViewModel
import com.theone.demo.databinding.FragmentLoginRegisterBinding
import com.theone.demo.viewmodel.AppViewModel
import com.theone.mvvm.ext.getAppViewModel
import com.theone.mvvm.core.base.fragment.BaseCoreFragment
import com.theone.mvvm.ext.qmui.showFailTipsDialog
import com.theone.mvvm.ext.qmui.showSuccessTipsExitDialog

class LoginRegisterItemFragment private constructor() :
    BaseCoreFragment<LoginRegisterViewModel, FragmentLoginRegisterBinding>() {

    companion object {
        fun newInstant(isRegister: Boolean): LoginRegisterItemFragment {
            return LoginRegisterItemFragment().bundle {
                putBoolean(TYPE, isRegister)
            }
        }
    }

    private val mAppVm: AppViewModel by lazy { getAppViewModel<AppViewModel>() }

    private val isRegister: Boolean by getValueNonNull(TYPE)

    override fun getBindingClick(): Any = ProxyClick()

    override fun initView(root: View) {
        root.setBackgroundColor(getColor(mActivity, R.color.white))
        getViewModel().isRegister.set(isRegister)
    }

    override fun createObserver() {
        getViewModel().run {
            getResponseLiveData().observe(this@LoginRegisterItemFragment, Observer {
                mAppVm.userInfo.value = it
                CacheUtil.setUser(it)
                showSuccessTipsExitDialog(if (isRegister.get()) "注册" else "登录" + "成功")
            })
            getErrorLiveData().observe(this@LoginRegisterItemFragment, Observer {
                showFailTipsDialog(it)
            })
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