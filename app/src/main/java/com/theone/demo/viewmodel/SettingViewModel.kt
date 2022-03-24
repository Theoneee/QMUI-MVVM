package com.theone.demo.viewmodel

import com.theone.demo.data.repository.ApiRepository
import com.theone.mvvm.core.base.viewmodel.BaseRequestViewModel

class SettingViewModel : BaseRequestViewModel<String>() {

    override fun requestServer() {

    }

    fun loginOut() {
        requestAwait(
            ApiRepository.INSTANCE.loginOut(),
            "退出中"
        )
    }

}