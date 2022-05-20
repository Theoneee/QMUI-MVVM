package com.theone.demo.ui.fragment.sample

import androidx.recyclerview.widget.RecyclerView
import com.luck.picture.lib.decoration.GridSpacingItemDecoration
import com.qmuiteam.qmui.widget.QMUITopBarLayout
import com.theone.common.ext.dp2px
import com.theone.demo.databinding.FragmentPictureSelectorBinding
import com.theone.demo.viewmodel.PictureSelectViewModel
import com.theone.mvvm.core.base.fragment.BasePictureSelectorFragment
import com.theone.mvvm.ext.qmui.setTitleWithBackBtn

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
 * @date 2022-05-20 14:31
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
class PictureSelectFragment:BasePictureSelectorFragment<PictureSelectViewModel, FragmentPictureSelectorBinding>() {

    override fun QMUITopBarLayout.initTopBar() {
        setTitleWithBackBtn("问题反馈",this@PictureSelectFragment)
    }

    override fun getRecyclerView(): RecyclerView  = getDataBinding().recyclerView

}