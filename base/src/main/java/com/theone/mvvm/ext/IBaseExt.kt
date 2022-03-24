package com.theone.mvvm.ext

import android.util.Log
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.core.util.forEach
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.theone.mvvm.base.IDataBinding
import com.theone.mvvm.base.IQMUI
import com.theone.mvvm.base.IViewModel
import com.theone.mvvm.base.viewmodel.BaseViewModel

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
 * @date 2021-04-01 13:47
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
fun <VM : BaseViewModel> IViewModel<VM>.createViewModel(owner: ViewModelStoreOwner): VM =
    ViewModelProvider(owner)[getViewModelClass()]

fun <DB : ViewDataBinding, VM : BaseViewModel> DB.initDataBinding(
    lifecycle: LifecycleOwner,
    binding: IDataBinding<DB>,
    viewModel: VM
) {
    lifecycleOwner = lifecycle
    with(binding) {
        setVariable(getBindingVmId(), viewModel)
        getBindingClick()?.let {
            setVariable(getBindingClickId(), it)
        }
        SparseArray<Any>().apply {
            applyBindingParams()
            forEach { key, any ->
                setVariable(key, any)
            }
        }.clear()
    }
}

fun IQMUI.addLoadingObserveExt(
    owner: LifecycleOwner,
    vararg viewModels: BaseViewModel
) {
    viewModels.forEach { viewModel ->
        with(viewModel){
            //显示弹窗
            getShowLoadingLiveData().observe(owner) {
                showLoading(it)
            }
            //关闭弹窗
            getHideLoadingLiveData().observe(owner) {
                hideLoading()
            }
            getShowProgressLiveData().observe(owner) {
                showProgress(it)
            }
            getHideProgressLiveData().observe(owner) {
                hideProgress()
            }
        }
    }
}

fun SparseArray<Any>.addParams(
    @NonNull variableId: Int,
    @NonNull any: Any
) {
    if (get(variableId) == null) {
        put(variableId, any)
    }
}
