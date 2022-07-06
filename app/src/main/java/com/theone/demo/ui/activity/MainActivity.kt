package com.theone.demo.ui.activity

import android.os.Bundle
import com.hjq.permissions.Permission
import com.hjq.permissions.XXPermissions
import com.qmuiteam.qmui.arch.annotation.DefaultFirstFragment
import com.theone.demo.ui.fragment.IndexFragment
import com.theone.mvvm.base.activity.BaseFragmentActivity
import com.theone.mvvm.core.base.callback.CoreOnPermission

@DefaultFirstFragment(IndexFragment::class)
class MainActivity : BaseFragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestPermission()
    }

    /**
     * 请求权限
     */
    private fun requestPermission() {
        XXPermissions.with(this)
            .permission(Permission.MANAGE_EXTERNAL_STORAGE)
            .request(object : CoreOnPermission(this) {

                override fun hasPermission(granted: MutableList<String>?, all: Boolean) {

                }

            })
    }
}