package com.theone.mvvm.base

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.viewbinding.ViewBinding
import com.qmuiteam.qmui.widget.QMUITopBarLayout

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
 * @date 2022-03-04 16:50
 * @describe View构造器
 * @email 625805189@qq.com
 * @remark
 */
abstract class ViewConstructor(val context: Context, private val factory: Factory) {

    private val mLayoutInflater: LayoutInflater = LayoutInflater.from(context)

    private val mRootView: ViewGroup by lazy {
        createRootView()
    }

    private val mContentView: View by lazy {
        factory.create(getRootView(), mLayoutInflater)
    }

    private val mTopBar: QMUITopBarLayout? by lazy {
        createTopBar()
    }

    abstract fun createRootView(): ViewGroup
    abstract fun createView(): View
    abstract fun createTopBar(): QMUITopBarLayout?
    fun getFactory(): Factory = factory
    fun getRootView(): ViewGroup = mRootView
    fun getContentView(): View = mContentView
    fun getTopBar(): QMUITopBarLayout? = mTopBar


    interface Factory {

        fun create(root: ViewGroup, layoutInflater: LayoutInflater): View

    }

    class DefaultFactory(private val layoutId: Int) : Factory {

        override fun create(root: ViewGroup, layoutInflater: LayoutInflater): View {
            return layoutInflater.inflate(layoutId, root, false)
        }

    }

    class DataBindingFactory<DB : ViewDataBinding>(
        private val clazz: Class<DB>,
        val init: (dataBinding: DB) -> Unit
    ) :
        Factory {

        private lateinit var mBinding: DB

        fun getDataBinding() = mBinding

        override fun create(root: ViewGroup, layoutInflater: LayoutInflater): View {
            mBinding = clazz.getDeclaredMethod(
                "inflate",
                LayoutInflater::class.java,
                ViewGroup::class.java,
                Boolean::class.java
            ).invoke(null, layoutInflater, root, false) as DB
            init(mBinding)
            return mBinding.root
        }

    }

    class ViewBindingFactory<VB : ViewBinding>(private val clazz: Class<VB>) : Factory {

        private lateinit var mBinding: VB

        fun getViewBinding() = mBinding

//        private inline fun <reified DB> createDB(root: ViewGroup, layoutInflater: LayoutInflater) =
//            DB::class.java.getDeclaredMethod(
//                "inflate",
//                LayoutInflater::class.java,
//                ViewGroup::class.java,
//                Boolean::class.java
//            ).invoke(null, layoutInflater, root, false) as DB


        override fun create(root: ViewGroup, layoutInflater: LayoutInflater): View {
            mBinding = clazz.getDeclaredMethod(
                "inflate",
                LayoutInflater::class.java,
                ViewGroup::class.java,
                Boolean::class.java
            ).invoke(null, layoutInflater, root, false) as VB
            return mBinding.root
        }
    }

}