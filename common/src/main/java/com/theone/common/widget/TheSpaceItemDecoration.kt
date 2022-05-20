package com.theone.common.widget

import android.graphics.Rect
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.theone.common.ext.TAG

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
 * @date 2021-03-30 14:40
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
class TheSpaceItemDecoration(
    private var column: Int,
    private val headerNum: Int,
    private val mLeft: Int,
    private val mRight: Int,
    private val mTop: Int,
    private val mBottom: Int
) :
    RecyclerView.ItemDecoration() {

    constructor(column: Int, space: Int, headerNum: Int = 0) : this(
        column,
        headerNum,
        space,
        space,
        space,
        space
    )

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        var columnIndex = 0
        val params = view.layoutParams as RecyclerView.LayoutParams
        val position = parent.getChildAdapterPosition(view)
        when (parent.layoutManager) {
            is StaggeredGridLayoutManager -> {
                params as StaggeredGridLayoutManager.LayoutParams
                columnIndex = params.spanIndex
            }
            is GridLayoutManager -> {
                params as GridLayoutManager.LayoutParams
                columnIndex = params.spanIndex
            }
            is LinearLayoutManager ->{
                column = 1
            }
        }
        // 有的时候适配器会加上头部，如果有就不加间距，让头部自行处理 （如果有尾部，同理也可以加上）
        if (position >= headerNum) {
            with(outRect) {
                // 先全都加上bottom,left,right间距
                left = mLeft
                right = mRight
                bottom = mBottom
                // 但是只给第一个加上top间距
                top = if (position == headerNum) mTop else 0
                if (column > 1) {
                    // 只要不为一列，只考虑最左和最右项的差别
                    Log.e(TAG, "getItemOffsets: columnIndex  $columnIndex" )
                    val curIndex = column - columnIndex
                    // 如果为多列时，给第一行的加上top.
                    // X != column 这个判断
                    // 当遇到SectionAdapter时，有两列，如果第一行是标题占据一整行，
                    if (position < column && curIndex != column) {
                        top = mTop
                    }
                    when (curIndex) {
                        column -> {
                            // 最左边的只需要在右边设置一半的间距
                            right = mRight / 2
                        }
                        1 -> {
                            // 最右边的只需要在左边设置一半的间距
                            left = mLeft / 2
                        }
                        else -> {
                            // 其余的一律在左右都设置一半的间距
                            left = mLeft / 2
                            right = mRight / 2
                        }
                    }
                }
                Log.e(TAG, "getItemOffsets: position: $position  left:  $left  top: $top  right:$right  bottom:$bottom     " )
            }
        }

    }

}