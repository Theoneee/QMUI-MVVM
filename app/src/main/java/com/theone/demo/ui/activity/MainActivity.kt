package com.theone.demo.ui.activity

import com.qmuiteam.qmui.arch.annotation.DefaultFirstFragment
import com.theone.demo.ui.fragment.IndexFragment
import com.theone.mvvm.base.activity.BaseFragmentActivity

@DefaultFirstFragment(IndexFragment::class)
class MainActivity : BaseFragmentActivity() {


}