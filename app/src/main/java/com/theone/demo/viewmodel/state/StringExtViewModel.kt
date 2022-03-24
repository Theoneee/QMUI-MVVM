package com.theone.demo.viewmodel.state

import com.theone.mvvm.base.viewmodel.BaseViewModel
import com.theone.common.callback.databind.SpannableStringObservableField

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
 * @date 2021-04-09 10:18
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
class StringExtViewModel:BaseViewModel() {

    val fruitName: SpannableStringObservableField = SpannableStringObservableField()
    val superscript: SpannableStringObservableField = SpannableStringObservableField()
    val subscript: SpannableStringObservableField = SpannableStringObservableField()
    val price: SpannableStringObservableField = SpannableStringObservableField()


}