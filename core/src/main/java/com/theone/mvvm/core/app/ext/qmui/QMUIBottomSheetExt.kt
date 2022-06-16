package com.theone.mvvm.core.app.ext.qmui

import android.content.Context
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import com.qmuiteam.qmui.widget.dialog.QMUIBottomSheet
import com.theone.mvvm.core.data.entity.QMUIItemBean
import com.theone.mvvm.entity.QMUIItem
import com.theone.mvvm.ext.qmui.NO_SET

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
 * @date 2021-05-27 14:48
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */

fun List<QMUIItem>.findSelect(content: String?): Int {
    if (content.isNullOrEmpty()) {
        return NO_SET
    }
    for ((index, item) in this.withIndex()) {
        if (item.getItemTitle() == content) {
            return index
        }
    }
    return NO_SET
}

fun <T : QMUIItem> Context.showBottomListSheet(
    items: List<T>,
    title: CharSequence?=null,
    markContent: String? = null,
    needMark: Boolean = true,
    allowDragDismiss: Boolean = true,
    cancelBtn: Boolean = true,
    gravityCenter: Boolean = true,
    listener: OnGridBottomSheetItemClickListener<T>
) =
    QMUIBottomSheet.BottomListSheetBuilder(this).apply {
        setTitle(title)
        setGravityCenter(gravityCenter)
        setAddCancelBtn(cancelBtn)
        setAllowDrag(allowDragDismiss)
        setNeedRightMark(needMark)
        setOnSheetItemClickListener { dialog, itemView, position, _ ->
            listener.onGridBottomSheetItemClick(dialog, itemView, items[position])
        }

        markContent?.let {
            val findIndex = items.findSelect(it)
            setCheckedIndex(findIndex)
        }

        for (item in items) {
            if (item.getItemNormalImage() != NO_SET) {
                addItem(
                    item.getItemNormalImage(),
                    item.getItemTitle().toString(),
                    item.getItemTitle().toString()
                )
            } else {
                addItem(item.getItemTitle().toString())
            }
        }

    }.build()!!


fun <T : QMUIItem> Context.showGridBottomSheet(
    items: List<T>,
    title: CharSequence?=null,
    column: Int = 4,
    allowDragDismiss: Boolean = true,
    cancelBtn: Boolean = true,
    listener: OnGridBottomSheetItemClickListener<T>
): QMUIBottomSheet {
    return QMUIBottomSheet.BottomGridSheetBuilder(this).apply {
        setTitle(title)
        setAllowDrag(allowDragDismiss)
        setAddCancelBtn(cancelBtn)
        for ((index, item) in items.withIndex()) {
            val style =
                if (column > index) QMUIBottomSheet.BottomGridSheetBuilder.FIRST_LINE else QMUIBottomSheet.BottomGridSheetBuilder.SECOND_LINE
            addItem(item.getItemNormalImage(), item.getItemTitle(), item, style)
        }
        setOnSheetItemClickListener { dialog, itemView ->
            listener.onGridBottomSheetItemClick(dialog, itemView, itemView.tag as T)
        }
    }.build()
}

interface OnGridBottomSheetItemClickListener<T : QMUIItem> {

    fun onGridBottomSheetItemClick(dialog: QMUIBottomSheet, itemView: View, item: T)

}
