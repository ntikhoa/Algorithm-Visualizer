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

    private var totalColumn = 20f
    private var columnHeightRatio = 22f
    private var columnWidth = 0f
    private var heightWeight = 0f

    private val MARGIN = 10f

    private val MAX_COLUMN_WIDTH = 100f
    private var MAX_COLUMN = 104f //TODO calculate max column

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        paint.color = ContextCompat.getColor(context, R.color.teal_700)

        heightWeight = getColumnHeightWeight()

        columnWidth = getColumnWidth()

        firstColumnPos = getFirstColumnPos()

        for (i in 0..(totalColumn - 1).toInt()) {
            drawSquare(canvas, i)
        }
    }

    private fun getColumnHeightWeight(): Float = screenHeight / columnHeightRatio
    private fun getColumnWidth(): Float {
        var columnWidth = (screenWidth - MARGIN * totalColumn) / totalColumn
        if (columnWidth > MAX_COLUMN_WIDTH) {
            columnWidth = MAX_COLUMN_WIDTH
        }
        return columnWidth
    }

    private fun getFirstColumnPos(): Float {
        val totalWidth = columnWidth * totalColumn + MARGIN * (totalColumn - 1)
        return (screenWidth - totalWidth) / 2
    }

    private fun drawSquare(canvas: Canvas?, index: Int) {
        val rect = RectF()
        rect.bottom = screenHeight
        rect.top = rect.bottom - (screenHeight - heightWeight * (totalColumn - 1 - index))
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