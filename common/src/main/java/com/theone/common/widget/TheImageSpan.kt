package com.theone.common.widget

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Paint.FontMetricsInt
import android.graphics.drawable.Drawable
import android.text.style.ImageSpan

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
 * @date 2019/6/20 0020
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
class TheImageSpan : ImageSpan {
    constructor(context: Context?, bitmap: Bitmap?) : super(context!!, bitmap!!) {}
    constructor(context: Context?, bitmap: Bitmap?, verticalAlignment: Int) : super(
        context!!,
        bitmap!!,
        verticalAlignment
    ) {
    }

    constructor(drawable: Drawable?) : super(drawable!!) {}

    override fun getSize(
        paint: Paint,
        text: CharSequence,
        start: Int,
        end: Int,
        fm: FontMetricsInt?
    ): Int {
        return try {
            val d = drawable
            val rect = d.bounds
            if (fm != null) {
                val fmPaint = paint.fontMetricsInt
                val fontHeight = fmPaint.bottom - fmPaint.top
                val drHeight = rect.bottom - rect.top
                val top = drHeight / 2 - fontHeight / 4
                val bottom = drHeight / 2 + fontHeight / 4
                fm.ascent = -bottom
                fm.top = -bottom
                fm.bottom = top
                fm.descent = top
            }
            rect.right
        } catch (e: Exception) {
            20
        }
    }

    override fun draw(
        canvas: Canvas,
        text: CharSequence,
        start: Int,
        end: Int,
        x: Float,
        top: Int,
        y: Int,
        bottom: Int,
        paint: Paint
    ) {
        try {
            val d = drawable
            canvas.save()
            var transY = 0
            transY = (bottom - top - d.bounds.bottom) / 2 + top
            canvas.translate(x, transY.toFloat())
            d.draw(canvas)
            canvas.restore()
        } catch (e: Exception) {
        }
    }
}