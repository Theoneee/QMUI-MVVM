package com.theone.demo.ui.fragment.sample

import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.ImageView
import com.qmuiteam.qmui.widget.QMUITopBarLayout
import com.qmuiteam.qmui.widget.grouplist.QMUICommonListItemView
import com.theone.common.ext.dp2px
import com.theone.demo.R
import com.theone.demo.databinding.FragmentGroupListViewBinding
import com.theone.demo.databinding.FragmentSampleGroupListViewBinding
import com.theone.mvvm.base.fragment.BaseVbFragment
import com.theone.mvvm.ext.qmui.*


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
 * @date 2021/2/25 0025
 * @describe QMUIGroupListView 的一些使用示例，具体请看QMUI文档（文档似乎也没多少内容）
 * @email 625805189@qq.com
 * @remark 这里最主要的是演示封装的一些扩展函数，方便使用
 */
class GroupListViewFragment : BaseVbFragment<FragmentGroupListViewBinding>(),
    CompoundButton.OnCheckedChangeListener, View.OnClickListener {

    override fun QMUITopBarLayout.initTopBar() {
        setTitleWithBackBtn(TAG, this@GroupListViewFragment)
    }

    override fun initView(root: View) {
        getViewBinding().groupListView.run {
            val normal = createItem("普通的Item")
            val detail = createItem("带有图标和详情的Item", "这是详情", R.drawable.svg_mine_project_address)
            val switch =
                createSwitchItem("带有Switch的Item", "这是详情", listener = this@GroupListViewFragment)

            val size = dp2px(35)
            val logo = ImageView(mActivity).apply {
                layoutParams = ViewGroup.LayoutParams(size, size)
                setImageResource(R.drawable.svg_heart)
            }

            val custom = createCustomViewItem("带有自定义View的Item", "设置自定义Item的详情", view = logo)

            val item = createItem("这个Item将被标记New")
            val item2 = createItem("这个Item将被标记红点")

            showTips(item, isDot = false)
            showTips(item2, showLeft = true)

            addToGroup(normal, detail, title = "这是标题", description = "", listener = this@GroupListViewFragment)
            addToGroup(switch, custom, listener = this@GroupListViewFragment)
            addToGroup(item, item2, title = "", description = "这是描述", listener = this@GroupListViewFragment)

        }
    }

    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
        showMsgTipsDialog("$isChecked")
    }

    override fun onClick(v: View?) {
        v as QMUICommonListItemView
        showInfoTipsDialog("点击了 $v.text")
    }

}