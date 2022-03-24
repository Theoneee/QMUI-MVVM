package com.theone.demo.ui.fragment.sample

import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.theone.demo.databinding.FragmentTestBinding
import com.theone.demo.ui.fragment.category.NavFragment
import com.theone.demo.ui.fragment.category.SystemFragment
import com.theone.demo.viewmodel.state.TestViewModel
import com.theone.mvvm.core.base.fragment.BaseCoreFragment
import java.util.ArrayList


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
 * @date 2021/3/10 0010
 * @describe BaseVmDbFragment 的使用示例
 * @email 625805189@qq.com
 * @remark
 */
class TestFragment : BaseCoreFragment<TestViewModel, FragmentTestBinding>() {

    override fun isStatusBarLightMode(): Boolean = true

    override fun showTopBar(): Boolean = false

    private val mAdapter: MyPagerAdapter by lazy {
        MyPagerAdapter(childFragmentManager).apply {
            addFragment(SystemFragment(), "体系")
            addFragment(NavFragment(), "导航")
        }
    }

    override fun initView(root: View) {
        getDataBinding().run {
            mViewPager.adapter = mAdapter
            mTabLayout.setupWithViewPager(mViewPager)
        }
    }

    override fun createObserver() {

    }

    inner class MyPagerAdapter(fragmentManager: FragmentManager) :
        FragmentStatePagerAdapter(fragmentManager) {
        private val myFragments: MutableList<Fragment> = ArrayList<Fragment>()
        private val myFragmentTitles: MutableList<String> = ArrayList()

        fun addFragment(fragment: Fragment, title: String) {
            myFragments.add(fragment)
            myFragmentTitles.add(title)
        }

        override fun getItem(position: Int): Fragment {
            return myFragments[position]
        }

        override fun getCount(): Int {
            return myFragments.size
        }

        override fun getPageTitle(position: Int): CharSequence {
            return myFragmentTitles[position]
        }
    }

}