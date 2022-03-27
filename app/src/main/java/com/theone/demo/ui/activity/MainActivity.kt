package com.theone.demo.ui.activity

import android.os.Bundle
import com.hjq.permissions.OnPermission
import com.hjq.permissions.Permission
import com.hjq.permissions.XXPermissions
import com.qmuiteam.qmui.arch.annotation.DefaultFirstFragment
import com.theone.demo.ui.fragment.IndexFragment
import com.theone.demo.ui.fragment.sample.LoaderTestFragment
import com.theone.mvvm.base.activity.BaseFragmentActivity

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
            .request(object : OnPermission {

                override fun hasPermission(granted: MutableList<String>?, all: Boolean) {

                }

                override fun noPermission(denied: MutableList<String>?, quick: Boolean) {

                }
            })
    }
}