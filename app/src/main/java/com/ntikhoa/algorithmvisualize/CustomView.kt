package com.ntikhoa.algorithmvisualize

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Nullable
import androidx.core.content.ContextCompat
import kotlin.properties.Delegates

class CustomView(context: Context, @Nullable attrs: AttributeSet) : View(context, attrs) {

    private var paint = Paint(Paint.ANTI_ALIAS_FLAG)

    private var screenWidth by Delegates.notNull<Float>()
    private var screenHeight by Delegates.notNull<Float>()

    private val DEFAULT_POS_X = 0

    private var WIDTH_OFFSET = 20f
    private var HEIGHT_OFFSET = 50f
    private val HEIGHT_RATIO = 22f

    private val totalColumn = 20f

    private val margin = 10f

    init {
        paint.color = ContextCompat.getColor(context, R.color.teal_700)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        WIDTH_OFFSET = (screenWidth - margin * totalColumn) / totalColumn
        HEIGHT_OFFSET = screenHeight / HEIGHT_RATIO
        for (i in 0..totalColumn.toInt()) {
            drawSquare(canvas, i)
        }
    }

    private fun drawSquare(canvas: Canvas?, index: Int) {
        val rect = RectF()
        rect.bottom = screenHeight
        rect.top = rect.bottom - (screenHeight - HEIGHT_OFFSET * (totalColumn - index))
        rect.left = (DEFAULT_POS_X + index * (WIDTH_OFFSET + margin))
        rect.right = rect.left + WIDTH_OFFSET

        if (index == 0) {
            rect.left += margin / 2f
        }
        canvas?.drawRect(rect, paint)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh);
        screenWidth = w.toFloat()
        screenHeight = h.toFloat()
        logSize("onSizeChanged", screenWidth, screenHeight)
    }

    private fun logSize(tag: String, width: Float, height: Float) {
        println("Debug: ${tag}: ${width} ${height}")
    }
}