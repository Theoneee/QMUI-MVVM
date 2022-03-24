package com.theone.mvvm.core.app.widge.loadsir.callback

import com.theone.mvvm.core.R


open class ErrorCallback : Callback() {

    override fun onCreateView(): Int {
        return R.layout.layout_error
    }

}