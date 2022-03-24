package com.theone.demo.ui.activity

import android.os.Bundle
import com.qmuiteam.qmui.arch.annotation.DefaultFirstFragment
import com.theone.demo.R
import com.theone.demo.ui.fragment.login.LoginRegisterFragment
import com.theone.mvvm.base.activity.BaseFragmentActivity


@DefaultFirstFragment(LoginRegisterFragment::class)
class LoginRegisterActivity: BaseFragmentActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overridePendingTransition(R.anim.scale_enter, R.anim.slide_still)
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.slide_still,R.anim.scale_exit)
    }

}