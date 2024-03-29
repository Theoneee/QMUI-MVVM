package com.theone.demo.ui.fragment

import android.view.View
import com.qmuiteam.qmui.arch.QMUIFragment
import com.qmuiteam.qmui.widget.tab.QMUITabBuilder
import com.theone.common.ext.appViewModels
import com.theone.demo.R
import com.theone.demo.ui.fragment.category.ClassificationFragment
import com.theone.demo.ui.fragment.home.HomeFragment
import com.theone.demo.ui.fragment.mine.MineFragment
import com.theone.demo.ui.fragment.project.ProjectFragment
import com.theone.demo.ui.fragment.gzh.WxGzhFragment
import com.theone.demo.viewmodel.AppViewModel
import com.theone.mvvm.base.viewmodel.BaseViewModel
import com.theone.mvvm.core.app.ext.qmui.addTab
import com.theone.mvvm.core.data.entity.QMUIItemBean
import com.theone.mvvm.core.base.fragment.BaseTabIndexFragment


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
 * @date 2021/3/1 0001
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
class IndexFragment : BaseTabIndexFragment<BaseViewModel>() {

    private val appVm: AppViewModel by appViewModels()

    override fun initTabAndFragments(
        tabs: MutableList<QMUIItemBean>,
        fragments: MutableList<QMUIFragment>
    ) {
        with(tabs) {
            // TODO R.drawable.svg_home_select 本身的颜色的是灰色的，选中变色是因为 Tips1
            addTab("首页", R.drawable.svg_home, R.drawable.svg_home_select)
            // skinChangeWithTintColor = false 时   颜色直接使用的是选中图标本身的颜色 使用 ?attr/app_skin_primary_color 填充
            addTab("项目", R.drawable.svg_project, R.drawable.svg_project_selected)
            addTab("分类", R.drawable.svg_classfication, R.drawable.svg_classification_selected)
            addTab("公众号", R.drawable.svg_wx_gzh, R.drawable.svg_wx_gzh_selected)
            addTab("我的", R.drawable.svg_mine, R.drawable.svg_mine_selected)
        }

        with(fragments) {
            add(HomeFragment())
            add(ProjectFragment())
            add(ClassificationFragment())
            add(WxGzhFragment())
            add(MineFragment())
        }

    }

    override fun createObserver() {
        super.createObserver()
        appVm.notifyFragment.observe(this){
            getViewPager().setCurrentItem(1,false)
        }
    }

}