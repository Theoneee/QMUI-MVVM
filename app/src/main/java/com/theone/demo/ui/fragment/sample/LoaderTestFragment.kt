package com.theone.demo.ui.fragment.sample

import android.view.View
import com.theone.common.ext.delay
import com.theone.demo.databinding.FragmentLoaderTestBinding
import com.theone.mvvm.base.viewmodel.BaseViewModel
import com.theone.mvvm.core.base.fragment.BaseCoreFragment

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
 * @date 2022-03-25 10:36
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */

class LoaderTestFragment : BaseCoreFragment<BaseViewModel, FragmentLoaderTestBinding>() {

//    override fun loaderRegisterView(): View? = null
//    override fun loaderRegisterView(): View = getDataBinding().content1
//    override fun loaderRegisterView(): View = getDataBinding().content2
//    override fun loaderRegisterView(): View = getDataBinding().center
//    override fun loaderRegisterView(): View = getDataBinding().root
    override fun loaderRegisterView(): View = getViewConstructor().getRootView()

    override fun initView(root: View) {
        getTopBar()?.setTitle("LoaderTestFragment")
        delay(1000){
            getDataBinding().stub.viewStub?.inflate()
        }
//        getLoaderView()?.run {
//            showLoadingPage("五十年以后")
//            delay(2000) {
//                showErrorPage("你还能在我左右？") {
//                    showLoadingPage("再试一次")
//                    delay(1000) {
//                        showSuccessPage()
//                    }
//                }
//            }
//        }
    }

}