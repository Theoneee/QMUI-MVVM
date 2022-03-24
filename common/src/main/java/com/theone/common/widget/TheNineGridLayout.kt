package com.theone.common.widget

import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.theone.common.R
import com.theone.common.callback.IImageUrl
import com.theone.common.ext.gone
import com.theone.common.ext.visible
import kotlin.math.ceil

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
 * @date 2021-04-25 15:16
 * @describe 九宫格图片展示自定义控件
 * @email 625805189@qq.com
 * @remark https://github.com/HMY314/NineGridLayout
 */
abstract class TheNineGridLayout(context: Context, attrs: AttributeSet? = null) :
    ViewGroup(context, attrs) {

    /**
     * 默认间距
     */
    private val DEFAULT_SPACING: Float = 8f

    /**
     * 最大显示数量
     */
    private val MAX_COUNT = 9

    /**
     * 图片地址
     */
    private val mUrlList = mutableListOf<IImageUrl>()

    /**
     * 列
     */
    private var mColumns: Int = 0

    /**
     * 行
     */
    private var mRows: Int = 0

    /**
     * 间距
     */
    private var mSpacing = DEFAULT_SPACING

    private var mTotalWidth: Int = 0
    private var mSingleWidth: Int = 0
    private var mIsFirst: Boolean = true
    private var mIsShowAll: Boolean = false


    init {
        val typeArray = context.obtainStyledAttributes(attrs, R.styleable.TheNineGridLayout)
        mSpacing = typeArray.getDimension(R.styleable.TheNineGridLayout_space, DEFAULT_SPACING)
        typeArray.recycle()
        checkSize()
    }

    fun <T : IImageUrl> setUrlList(data: List<T>?) {
        if(null == data){
            gone()
            return
        }
        visible()
        mUrlList.clear()
        mUrlList.addAll(data)
        if (!mIsFirst) {
            notifyDataSetChanged()
        }
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        mTotalWidth = right - left
        mSingleWidth = ((mTotalWidth - (mSpacing * (3 - 1))) / 3).toInt()
        if (mIsFirst) {
            notifyDataSetChanged()
            mIsFirst = false
        }
    }

    private fun notifyDataSetChanged() {
        post {
            refresh()
        }
    }

    private fun refresh() {
        removeAllViews()
        if(!checkSize()) return
        when (val size = mUrlList.size) {
            1 -> createOneImageView()
            else -> {
                generateChildrenLayout()
                //根据子view数量确定高度
                layoutParams = layoutParams.also {
                    it.height = (mSingleWidth * mRows + mSpacing * (mRows - 1)).toInt()
                }
                for ((i, item) in mUrlList.withIndex()) {
                    val imageView = createImageView(i, item)
                    val showNum = !mIsShowAll && size > MAX_COUNT && i == MAX_COUNT
                    layoutImageView(imageView, i, item, showNum)
                    if(showNum) break
                }
            }
        }
    }

    private fun createOneImageView() {
        val item = mUrlList[0]
        layoutParams = layoutParams.also {
            it.height = mSingleWidth
        }
        createImageView(0, item).let {
            it.layout(0, 0, mSingleWidth, mSingleWidth)
            val isShowDefault = displayOneImage(it, item, mTotalWidth)
            if (isShowDefault) {
                // 使用默认九宫格的方式显示
                layoutImageView(it, 0, item, false)
            } else {
                addView(it)
            }
        }
    }

    private fun layoutImageView(imageView: ImageView, i: Int, data: IImageUrl, showNum: Boolean) {
        val singleWidth = ((mTotalWidth - mSpacing * (3 - 1)) / 3).toInt()
        val index = findPosition(i)
        val left = ((singleWidth + mSpacing) * index[1]).toInt()
        val top = ((singleWidth + mSpacing) * index[0]).toInt()
        val right = left + singleWidth
        val bottom = top + singleWidth

        imageView.layout(left, top, right, bottom)
        addView(imageView)
        if (showNum) {
            val overCount = mUrlList.size - MAX_COUNT
            addView(TextView(context).apply {
                text = overCount.toString()
                setTextColor(Color.WHITE)
                textSize = 30F
                gravity = Gravity.CENTER
                setPadding(0, singleWidth / 2 - getFontHeight(textSize), 0, 0)
                setBackgroundColor(Color.BLACK)
                background.alpha = 120
                layout(left, top, right, bottom)
            })
        }
        displayImage(imageView, data)
    }


    private fun findPosition(childNum: Int): IntArray {
        val position = IntArray(2)
        for (i in 0 until mRows) {
            for (j in 0 until mColumns) {
                if (i * mColumns + j == childNum) {
                    position[0] = i //行
                    position[1] = j //列
                    break
                }
            }
        }
        return position
    }

    /**
     * 根据图片个数确定行列数量
     */
    private fun generateChildrenLayout() {
        val size = mUrlList.size
        if (size <= 3) {
            mRows = 1
            mColumns = size
        } else if (size <= 6) {
            mRows = 2
            mColumns = 3
            if (size == 4) {
                mColumns = 2
            }
        } else {
            mColumns = 3
            if (mIsShowAll) {
                mRows = size / 3
                val b = size % 3
                if (b > 0) {
                    mRows++
                }
            } else {
                mRows = 3
            }
        }
    }


    /**
     * 检查数据，判断是否要显示
     */
    private fun checkSize():Boolean {
        return if (mUrlList.isNullOrEmpty()){
            gone()
            false
        } else{
            visible()
            true
        }
    }

    private fun getFontHeight(fontSize: Float): Int {
        val paint = Paint()
        paint.textSize = fontSize
        val fm = paint.fontMetrics
        return ceil(fm.descent - fm.ascent.toDouble()).toInt()
    }

    protected open fun setOneImageLayoutParams(
        imageView: ImageView,
        width: Int,
        height: Int
    ) {
        imageView.layoutParams = LayoutParams(width, height)
        imageView.layout(0, 0, width, height)
        layoutParams = layoutParams.also {
            it.height = height
        }
    }

    protected open fun createImageView(i: Int, data: IImageUrl): ImageView {
        return TheRatioImageView(context).apply {
            scaleType = ImageView.ScaleType.CENTER_CROP
            setOnClickListener {
                onImageItemClick(this, mUrlList, i)
            }
        }
    }

    fun IImageUrl.getUrl():String{
        getThumbnail()?.let {
            if(it.isNotEmpty()){
                return it
            }
        }
        return getImageUrl()
    }

    /**
     * @return Boolean  true 代表按照九宫格默认大小显示，false 代表按照自定义宽高显示
     */
    abstract fun displayOneImage(imageView: ImageView, item: IImageUrl, parentWidth: Int): Boolean

    abstract fun displayImage(imageView: ImageView, item: IImageUrl)

    abstract fun onImageItemClick(view: View, urlList: List<IImageUrl>, position: Int)

}