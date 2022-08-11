package com.theone.demo.ui.fragment.sample

import android.view.View
import com.hjq.toast.ToastUtils
import com.qmuiteam.qmui.widget.QMUITopBarLayout
import com.qmuiteam.qmui.widget.dialog.QMUIBottomSheet
import com.qmuiteam.qmui.widget.grouplist.QMUICommonListItemView
import com.theone.demo.R
import com.theone.demo.databinding.FragmentGroupListViewBinding
import com.theone.demo.databinding.FragmentSampleGroupListViewBinding
import com.theone.mvvm.base.fragment.BaseVbFragment
import com.theone.mvvm.core.app.ext.qmui.OnGridBottomSheetItemClickListener
import com.theone.mvvm.core.app.ext.qmui.showBottomListSheet
import com.theone.mvvm.core.app.ext.qmui.showGridBottomSheet
import com.theone.mvvm.core.data.entity.QMUIItemBean
import com.theone.mvvm.entity.QMUIItem
import com.theone.mvvm.ext.qmui.addToGroup
import com.theone.mvvm.ext.qmui.createItem
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
 * @date 2022-06-16 08:46
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
class QMUIBottomSheetFragment : BaseVbFragment<FragmentGroupListViewBinding>(),
    View.OnClickListener {

    private lateinit var mListItem: QMUICommonListItemView
    private lateinit var mGridItem: QMUICommonListItemView

    override fun QMUITopBarLayout.initTopBar() {
        setTitleWithBackBtn(TAG,this@QMUIBottomSheetFragment)
    }

    override fun initView(root: View) {
        getViewBinding().groupListView.run {
            mListItem = createItem("ShowBottomListSheet")
            mGridItem = createItem("ShowBottomGridSheet")
            addToGroup(mListItem, mGridItem, title = "", listener = this@QMUIBottomSheetFragment)
        }
    }

    private val mTestList: List<TestBean> by lazy {
        mutableListOf<TestBean>().apply {
            for (index in 0..20) {
                add(TestBean("Item $index"))
            }
        }
    }

    private val mTestGrid: List<QMUIItemBean> by lazy {
        mutableListOf<QMUIItemBean>().apply {
            for (index in 0..6) {
                add(QMUIItemBean("下载 $index", R.drawable.icon_more_operation_save))
            }
        }
    }

    private fun showBottomListSheet() {
        val selectStr = mListItem.detailText.toString()
        requireContext().showBottomListSheet(
            mTestList,
            "ListSheet",
            markContent = selectStr,
            listener = object : OnGridBottomSheetItemClickListener<TestBean> {
                override fun onGridBottomSheetItemClick(
                    dialog: QMUIBottomSheet,
                    itemView: View,
                    item: TestBean
                ) {
                    dialog.dismiss()
                    mListItem.detailText = item.getItemTitle()
                }
            }).show()
    }

    private fun showBottomGridSheet() {
        requireContext().showGridBottomSheet(
            mTestGrid,
            title = "GridSheet",
            column = 4,
            allowDragDismiss = true,
            cancelBtn = true,
            listener = object : OnGridBottomSheetItemClickListener<QMUIItemBean> {
                override fun onGridBottomSheetItemClick(
                    dialog: QMUIBottomSheet,
                    itemView: View,
                    item: QMUIItemBean
                ) {
                    dialog.dismiss()
                    ToastUtils.show("点击了 ${item.getItemTitle()}")
                }
            }).show()
    }

    override fun onClick(v: View?) {
        when (v) {
            mListItem -> {
                showBottomListSheet()
            }
            mGridItem -> {
                showBottomGridSheet()
            }
            else -> {

            }
        }
    }

    inner class TestBean(val title: String) : QMUIItem {

        override fun getItemTitle(): CharSequence = title

    }

}