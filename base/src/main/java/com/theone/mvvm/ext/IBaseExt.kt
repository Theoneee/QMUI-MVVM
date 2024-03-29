package com.theone.mvvm.ext

import android.os.Build
import android.util.Log
import android.util.SparseArray
import androidx.annotation.NonNull
import androidx.core.util.forEach
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.theone.mvvm.base.IDataBinding
import com.theone.mvvm.base.IQMUI
import com.theone.mvvm.base.IViewModel
import com.theone.mvvm.base.viewmodel.BaseViewModel
import java.lang.reflect.ParameterizedType

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
        applyBindingParams()
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
                this@addLoadingObserveExt.showLoadingDialog(it)
            }
            //关闭弹窗
            getHideLoadingLiveData().observe(owner) {
                this@addLoadingObserveExt.hideLoadingDialog()
            }
            getShowProgressLiveData().observe(owner) {
                showProgressDialog(it)
            }
            getHideProgressLiveData().observe(owner) {
                this@addLoadingObserveExt.hideProgressDialog()
            }
        }
    }
}

/**
 * 获取当前类绑定的泛型clazz
 */
@Suppress("UNCHECKED_CAST")
fun <T> Any.getClazz(index: Int = 0): T {
    return (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[index] as T
}

