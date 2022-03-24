package com.theone.mvvm.core.base.fragment

import android.util.SparseArray
import android.view.KeyEvent
import android.view.View
import androidx.databinding.ViewDataBinding
import com.hjq.toast.ToastUtils
import com.theone.mvvm.base.fragment.BaseVmDbFragment
import com.theone.mvvm.base.viewmodel.BaseViewModel
import com.theone.mvvm.core.R
import com.theone.mvvm.core.base.callback.ICore
import com.theone.mvvm.core.app.ext.hideProgressDialog
import com.theone.mvvm.core.app.ext.registerLoadSir
import com.theone.mvvm.core.app.ext.showProgressDialog
import com.theone.mvvm.core.app.widge.loadsir.core.LoadService
import com.theone.mvvm.entity.ProgressBean

/**
 * @author The one
 * @date 2021/3/23 0022
 * @describe CoreBaseFragment
 * @email 625805189@qq.com
 * @remark 添加界面状态管理
 */
abstract class BaseCoreFragment<VM : BaseViewModel, DB : ViewDataBinding> :
    BaseVmDbFragment<VM, DB>(), ICore {

    /**
     * 界面状态管理者
     */
    private var mLoadSir: LoadService<Any>? = null

    override fun getLoadSir(): LoadService<Any>? = mLoadSir

    override fun onViewCreated(rootView: View) {
        mLoadSir = registerLoadSir()
        super.onViewCreated(rootView)
    }

    /**
     * LoadSir用来注册的View,如果不需要注册返回null.
     * @return View
     * 为了减少界面的嵌套层级，这里默认返回null了
     */
    override fun loadSirRegisterView(): View? = null

    override fun showProgress(progress: ProgressBean) {
        requireActivity().showProgressDialog(progress)
    }

    override fun hideProgress() {
        hideProgressDialog()
    }

    /**
     * 把下面这些方法实现了，子类需要的时候重写，免的每次都要去实现这个方法
     */

    override fun SparseArray<Any>.applyBindingParams() {}

    override fun onLazyInit() {}

    override fun initData() {}

    override fun onPageReLoad() {}

    override fun isExitPage(): Boolean = false

    override fun showExitTips() {
        ToastUtils.show(R.string.core_exit_tips)
    }

    private var exitTime: Long = 0

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && isExitPage()) {
            if (System.currentTimeMillis() - exitTime > 2000) {
                //弹出提示，可以有多种方式
                exitTime = System.currentTimeMillis()
                showExitTips()
            } else {
                requireActivity().finish()
            }
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

}