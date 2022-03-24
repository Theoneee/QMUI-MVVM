package com.theone.mvvm.core.app.ext.qmui

import android.content.Context
import android.view.View
import com.qmuiteam.qmui.widget.dialog.QMUIBottomSheet
import com.theone.mvvm.core.data.entity.QMUIItem

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

fun Context.showGridBottomSheet(
    items: List<QMUIItem>,
    column: Int = 4,
    cancel: Boolean = true,
    listener: OnGridBottomSheetItemClickListener
): QMUIBottomSheet {
    return QMUIBottomSheet.BottomGridSheetBuilder(this).apply {
        setAddCancelBtn(cancel)
        for ((index, item) in items.withIndex()) {
            val style =
                if (column > index) QMUIBottomSheet.BottomGridSheetBuilder.FIRST_LINE else QMUIBottomSheet.BottomGridSheetBuilder.SECOND_LINE
            addItem(item.res, item.title, item.title, style)
        }
        setOnSheetItemClickListener { dialog, itemView ->
            val tag = itemView.tag as String
            listener.onGridBottomSheetItemClick(dialog, itemView, tag)
        }
    }.build()
}

interface OnGridBottomSheetItemClickListener{

    fun onGridBottomSheetItemClick(dialog: QMUIBottomSheet, itemView: View, tag: String)

}
