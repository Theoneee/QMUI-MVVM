package com.theone.demo.app.widget

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.theone.common.ext.px2dp
import kotlin.random.Random

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
 * @date 2021-06-18 09:45
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
class TheLineView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : View(context, attrs, defStyle) {

    val size = px2dp(80).toFloat()

    private val mPoints = arrayListOf<ViewPoint>(
        ViewPoint(0f, 0f),
        ViewPoint(200f, 200f),
        ViewPoint(500f, 320f),
        ViewPoint(700f, 830f),
        ViewPoint(900f, 550f),
        ViewPoint(1000f, 340f)
    )

    private val mZonePaint = Paint().apply {
        style = Paint.Style.STROKE
        color = Color.RED
        strokeWidth = 12f
    }

    private val cPaint = Paint().apply {
        style = Paint.Style.FILL
        color = Color.RED
        strokeWidth = 12f
    }

    private val textPaint = Paint().apply {
        color = Color.WHITE
        textSize = 30f
        strokeWidth = 12f
    }

    private val textPadding = 12f

    private val textBgPaint = getTextBackGroundPaint(225, 165, 65)

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        with(canvas) {
            changeCanvasXY()
            save()
//            drawGirdView()
            drawLine()
            drawViewPoint()
            drawViewTexts()
            drawXAndY()
        }
    }

    private fun Canvas.changeCanvasXY() {
        // 改变坐标轴方向
        scale(1f, -1f)
        // 改变坐标系方向
        translate(0f, -(measuredHeight.toFloat()))
    }

    private fun Canvas.drawGirdView() {
        val paint = Paint().apply {
            style = Paint.Style.STROKE
            color = Color.WHITE
            strokeWidth = 2f
        }

        // 横着的线条
        val pathX = Path().apply {
            moveTo(0f, size)
            lineTo(measuredWidth.toFloat(), size)
        }
        // 竖着的线条
        val pathY = Path().apply {
            moveTo(size, 0f)
            lineTo(size, measuredHeight.toFloat())
        }

        // X轴的线条数
        val countX = (measuredWidth / size).toInt()
        // Y轴的线条数
        val countY = (measuredHeight / size).toInt()

        save()

        // 沿着X轴画竖着的线
        for (index in 0 until countX) {
            drawPath(pathY, paint)
            // 每次往X轴方向移动
            translate(size, 0f)
        }

        restore()

        // 沿着Y轴画横着的线
        for (index in 0 until countY) {
            drawPath(pathX, paint)
            // 每次往Y轴方向移动
            translate(0f, size)
        }
    }

    private fun Canvas.drawXAndY() {
        val x_paint = Paint().apply {
            style = Paint.Style.STROKE
            color = Color.BLUE
            strokeWidth = 10f
        }

        val y_paint = Paint().apply {
            style = Paint.Style.STROKE
            color = Color.BLUE
            strokeWidth = 10f
        }

        val path=Path().apply {
            moveTo(0f,0f)
            lineTo(0f,measuredHeight+10f)
        }

        //Y轴的箭头绘制
        val verticlePath=Path().apply {
            moveTo(-20f,measuredHeight-60f)
            lineTo(0f,measuredHeight-40f)
            lineTo(20f,measuredHeight-60f)
        }

        path.addPath(verticlePath)

        //画y轴
        drawPath(path,y_paint)

        val pathx=Path().apply {
            moveTo(0f,0f)
            lineTo(measuredWidth-20f,0f)
        }


        val horizontalPath=Path().apply {
            moveTo(measuredWidth-60f,20f)
            lineTo(measuredWidth-40f,0f)
            lineTo(measuredWidth-60f,-20f)
        }
        pathx.addPath(horizontalPath)
        //画x轴
        drawPath(pathx,x_paint)
    }

    private fun Canvas.drawLine() {
        val path = Path().apply {
            mPoints.forEach {
                lineTo(it.x, it.y)
            }

        }
        drawPath(path, mZonePaint)
        with(path) {
            lineTo(mPoints[mPoints.size - 1].x, 0f)
            close()
        }
        with(mZonePaint) {
            style = Paint.Style.FILL
            shader = getBgShader()
        }
        drawPath(path, mZonePaint)
    }

    /**
     * 画转折点圆
     * @receiver Canvas
     * @param point ViewPoint
     */
    private fun Canvas.drawViewPoint() {
        mPoints.forEach {
            drawCircle(it.x, it.y, 16f, cPaint)
        }
    }

    private fun Canvas.drawViewTexts() {
        mPoints.forEach {
            if (it.y.toInt() != 0)
                drawText("${it.y.toInt()}万", it, textPaint, textBgPaint)
        }
    }

    private fun Canvas.drawText(
        content: String,
        point: ViewPoint,
        paint: Paint,
        textBgPaint: Paint
    ) {
        save()
        val textHeight = textBgPaint.getTextHeight()
        translate(point.x, point.y + textHeight)
        scale(1f, -1f)
        rotate(-10.0f)
        drawRoundRect(
            RectF(
                0f,
                -textHeight,
                textBgPaint.getTextWidth(content) + textPadding * 2,
                textHeight / 2
            ), 10f, 10f, textBgPaint
        )
        drawText(content, 0, content.length, textPadding, 0f, paint)
        restore()
    }

    private fun Paint.getTextHeight(): Float {
        return with(fontMetrics) {
            bottom - top + leading
        }
    }

    private fun Paint.getTextWidth(textStr: String): Float {
        return measureText(textStr)
    }

    private fun getTextBackGroundPaint(alpha: Int, centerAlpha: Int, endAlpha: Int): Paint =
        Paint().apply {
            textSize = 30f
            shader = getTextBgShader(alpha, centerAlpha, endAlpha)
            isAntiAlias = true
        }

    private fun getBgShader(): Shader {
        return LinearGradient(
            (measuredWidth / 2).toFloat(),
            measuredHeight.toFloat(),
            (measuredWidth / 2).toFloat(),
            0f,
            getRandomShaderColors(),
            null,
            Shader.TileMode.CLAMP
        )
    }

    /**
     * 文字背景
     * @param alpha Int
     * @param centerAlpha Int
     * @param endAlpha Int
     * @return Shader
     */
    private fun getTextBgShader(
        alpha: Int = 225,
        centerAlpha: Int = 165,
        endAlpha: Int = 65
    ): Shader {
        return LinearGradient(
            0f,
            0f,
            44f,
            44f,
            getRandomShaderColors(alpha, centerAlpha, endAlpha),
            null,
            Shader.TileMode.CLAMP
        )
    }

    private fun getRandomShaderColors(
        alpha: Int = 225,
        centerAlpha: Int = 165,
        endAlpha: Int = 65
    ): IntArray {
        val random = Random(225)
        val R = random.nextInt()
        val G = random.nextInt()
        val B = random.nextInt()

        val R1 = random.nextInt()
        val G1 = random.nextInt()
        val B1 = random.nextInt()

        val R2 = random.nextInt()
        val G2 = random.nextInt()
        val B2 = random.nextInt()
        return intArrayOf(
            Color.argb(alpha, R, G, B),
            Color.argb(centerAlpha, R1, G1, B1),
            Color.argb(endAlpha, R2, G2, B2)
        )
    }

    data class ViewPoint(var x: Float, var y: Float)

}