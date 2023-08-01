package com.theone.mvvm.ext.qmui

import android.content.Context
import android.content.DialogInterface
import android.text.InputType
import com.qmuiteam.qmui.widget.dialog.QMUIDialog
import com.qmuiteam.qmui.widget.dialog.QMUIDialog.MenuDialogBuilder
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction.ACTION_PROP_POSITIVE
import com.qmuiteam.qmui.widget.dialog.QMUIDialogBuilder


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
 * @date 2021/3/15 0015
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */

/**
 * 消息类型弹窗
 */
fun Context.showMsgDialog(
    title: String?=null,
    message: CharSequence?,
    leftAction: String? = "取消",
    rightAction: String? = "确定",
    listener: QMUIDialogAction.ActionListener,
    @QMUIDialogAction.Prop prop: Int = ACTION_PROP_POSITIVE
): QMUIDialog {
    return QMUIDialog.MessageDialogBuilder(this).run {
        setTitle(title)
        if (!message.isNullOrEmpty()) {
            setMessage(message)
        }
        leftAction?.let {
            addAction(it, listener)
        }
        rightAction?.let {
            addAction(0, it, prop, listener)
        }
        show()
    }
}

/**
 * 菜单类型弹窗
 */
fun Context.showMenuDialog(
    title: String? = null,
    items: Array<CharSequence?>,
    listener: DialogInterface.OnClickListener
): QMUIDialog {
    return MenuDialogBuilder(this).run {
        setTitle(title)
        addItems(items, listener)
        show()
    }
}

/**
 * 单选类型弹窗
 */
fun <T:CharSequence> Context.showSingleChoiceDialog(
    title: String? = null,
    items: Array<T>,
    checkedIndex: Int,
    listener: DialogInterface.OnClickListener? = null
): QMUIDialog {
    return QMUIDialog.CheckableDialogBuilder(this).run {
        setTitle(title)
        setCheckedIndex(checkedIndex)
        addItems(items, listener)
        show()
    }
}

/**
 * 多选类型弹窗
 */
fun <T:CharSequence>  Context.showMultiChoiceDialog(
    title: String? = null,
    items: Array<T>,
    checkedItems: IntArray,
    leftAction: String,
    rightAction: String,
    listener: OnMultiChoiceConfirmClickListener
): QMUIDialog {
    return QMUIDialog.MultiCheckableDialogBuilder(this).run {
        setTitle(title)
        addItems(items,null)
        setCheckedItems(checkedItems)
        addAction(leftAction) { dialog, _ -> dialog?.dismiss() }
        addAction(rightAction) { dialog, _ -> listener.onItemSelected(dialog,checkedItemIndexes) }
        show()
    }
}

interface OnMultiChoiceConfirmClickListener{
    fun onItemSelected(dialog:QMUIDialog,checkedItems: IntArray)
}

fun Context.showEditDialog(title:String,content:String = "",hint:String?=null,inputType:Int = InputType.TYPE_CLASS_TEXT,leftAction: String = "取消",rightAction: String = "确定",
listener: QMUIEditDialogListener, @QMUIDialogAction.Prop prop: Int = ACTION_PROP_POSITIVE):QMUIDialog{
  val builder =   QMUIDialog.EditTextDialogBuilder(this).apply {
        setTitle(title)
        setDefaultText(content)
        setPlaceholder(hint)
        setInputType(inputType)
        addAction(leftAction){ dialog, index -> listener.onEditDialogClick(dialog,"",index) }
        addAction(0, rightAction, prop){ dialog, index -> listener.onEditDialogClick(dialog,editText.text.toString(),index) }
    }
    val dialog = builder.show()
    if(content.isNotEmpty()){
        builder.editText.setSelection(content.length)
    }
   return dialog
}

interface QMUIEditDialogListener{
    fun onEditDialogClick(dialog:QMUIDialog,content:String,index:Int)
}
