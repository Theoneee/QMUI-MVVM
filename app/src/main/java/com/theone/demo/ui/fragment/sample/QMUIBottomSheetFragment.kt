package com.theone.demo.ui.fragment.sample

import android.view.View
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.hjq.toast.ToastUtils
import com.qmuiteam.qmui.alpha.QMUIAlphaImageButton
import com.qmuiteam.qmui.layout.QMUIButton
import com.qmuiteam.qmui.widget.QMUITopBarLayout
import com.qmuiteam.qmui.widget.dialog.QMUIBottomSheet
import com.qmuiteam.qmui.widget.grouplist.QMUICommonListItemView
import com.theone.demo.R
import com.theone.demo.databinding.FragmentGroupListViewBinding
import com.theone.mvvm.base.fragment.BaseVbFragment
import com.theone.mvvm.core.app.ext.qmui.OnGridBottomSheetItemClickListener
import com.theone.mvvm.core.app.ext.qmui.createListPopup
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
    private lateinit var mPopupItem: QMUICommonListItemView
    private lateinit var mMoreBtn: QMUIAlphaImageButton

    override fun QMUITopBarLayout.initTopBar() {
        setTitleWithBackBtn(TAG, this@QMUIBottomSheetFragment)
        mMoreBtn = addRightImageButton(R.drawable.mz_titlebar_ic_more_dark,R.id.topbar_right_view).apply {
            setOnClickListener {
                showPopupDialog(it,mPopupDatas)
            }
        }
    }

    override fun initView(root: View) {
        getViewBinding().groupListView.run {
            mListItem = createItem("ShowBottomListSheet")
            mGridItem = createItem("ShowBottomGridSheet")
            mPopupItem = createItem("ShowPopupDialog")
            addToGroup(
                mListItem,
                mGridItem,
                mPopupItem,
                title = "",
                listener = this@QMUIBottomSheetFragment
            )
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

    private val mPopupDatas: List<QMUIItemBean> by lazy {
        mutableListOf<QMUIItemBean>().apply {
            for (index in 0..6) {
                add(QMUIItemBean("Item $index", R.drawable.mz_titlebar_ic_search_dark))
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

    private fun showPopupDialog(view: View,items: List<QMUIItem>,width:Int = 400,height:Int = 600){
        mActivity.createListPopup(items,
            { _, _, position ->
                ToastUtils.show("Click $position")
            }, width,height).show(view)
    }

    override fun onClick(v: View?) {
        when (v) {
            mListItem -> {
                showBottomListSheet()
            }
            mGridItem -> {
                showBottomGridSheet()
            }
            mPopupItem -> {
                showPopupDialog(v,mTestList,800,1000)
            }
            else -> {

            }
        }
    }

    inner class TestBean(val title: String) : QMUIItem {

        override fun getItemTitle(): CharSequence = title

    }

}