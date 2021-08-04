package com.ntikhoa.algorithmvisualize

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import androidx.annotation.Nullable
import androidx.core.content.ContextCompat

class CustomView(context: Context, @Nullable attrs: AttributeSet) : View(context, attrs) {

    private var paint = Paint(Paint.ANTI_ALIAS_FLAG)

    private var screenWidth = 0f
    private var screenHeight = 0f

    private var firstColumnPos = 0f

    private var columnWidth = 20f
    private var columnHeightRatio = 22f
    private val HEIGHT_OFFSET
        get() = screenHeight / columnHeightRatio

    private var totalColumn = 20f

    private val MARGIN = 10f

    private val MAX_COLUMN_WIDTH = 100f
    private var MAX_COLUMN = 104f

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        paint.color = ContextCompat.getColor(context, R.color.teal_700)

        columnWidth = (screenWidth - MARGIN * totalColumn) / totalColumn

        if (columnWidth > MAX_COLUMN_WIDTH) {
            columnWidth = MAX_COLUMN_WIDTH
        }
        val totalWidth = columnWidth * totalColumn + MARGIN * (totalColumn - 1)
        firstColumnPos = (screenWidth - totalWidth) / 2

        for (i in 0..(totalColumn - 1).toInt()) {
            drawSquare(canvas, i)
        }
    }

    private fun drawSquare(canvas: Canvas?, index: Int) {
        val rect = RectF()
        rect.bottom = screenHeight
        rect.top = rect.bottom - (screenHeight - HEIGHT_OFFSET * (totalColumn - index))
        rect.left = firstColumnPos + index * (columnWidth + MARGIN)
        rect.right = rect.left + columnWidth

        canvas?.drawRect(rect, paint)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh);
        screenWidth = w.toFloat()
        screenHeight = h.toFloat()
    }

    private fun logSize(tag: String, width: Float, height: Float) {
        println("Debug: ${tag}: ${width} ${height}")
    }

    fun setTotalSize(totalSize: Int) {
        if (totalSize < 0)
            this.totalColumn = 0f
        else if (totalSize > MAX_COLUMN) {
            //TODO: add later
        } else {
            this.totalColumn = totalSize.toFloat()
            columnHeightRatio = totalColumn + 2f
        }
        invalidate()
    }
}