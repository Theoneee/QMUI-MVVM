package com.theone.demo.ui.fragment.category

import com.theone.demo.ui.fragment.base.BaseArticleFragment
import com.theone.demo.viewmodel.PlazaViewModel

class PlazaFragment: BaseArticleFragment<PlazaViewModel>() {

    override fun createObserver() {
        super.createObserver()
        mAppVm.shareArticle.observe(this){
            onAutoRefresh()
        }
    }

}