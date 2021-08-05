package com.ntikhoa.algorithmvisualize

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import androidx.annotation.Nullable
import androidx.core.content.ContextCompat

class CustomView(context: Context, @Nullable attrs: AttributeSet) : View(context, attrs) {

    private var setSizeCalled = false

    private var DEFAULT_COLUMN = 20
    private val MARGIN = 10f
    private val MIN_WIDTH = 10f


    private var paint = Paint(Paint.ANTI_ALIAS_FLAG)

    private var screenWidth = 0f
    private var screenHeight = 0f

    private var maxColumn = 0

    private var firstColumnPos = 0f

    private var totalColumn = DEFAULT_COLUMN
    private var columnWidth = 0f
    private var heightWeight = 0f

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        paint.color = ContextCompat.getColor(context, R.color.teal_700)

        calculateAttribute()

        for (i in 0 until totalColumn) {
            drawSquare(canvas, i)
        }
    }

    private fun calculateAttribute() {
        screenWidth = width.toFloat()
        screenHeight = height.toFloat()

        maxColumn = getMaxColumn()

        totalColumn = if (maxColumn < DEFAULT_COLUMN) {
            maxColumn
        } else DEFAULT_COLUMN

        if (setSizeCalled) {
            applyTotalSize()
        }

        heightWeight = getColumnHeightWeight()
        calColWidthAndFirstPos()
    }

    private fun getColumnHeightWeight(): Float {
        val columnHeightRatio = totalColumn + 2
        return screenHeight / columnHeightRatio
    }

    private fun calColWidthAndFirstPos() {
        columnWidth = getColumnWidth()
        firstColumnPos = getFirstColumnPos()
    }

    private fun getColumnWidth(): Float = (screenWidth - MARGIN * totalColumn) / totalColumn

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

    private fun getMaxColumn(): Int {
        return ((screenWidth + MARGIN) / (MIN_WIDTH + MARGIN)).toInt()
    }

    private var userSize = 0

    fun setTotalSize(totalSize: Int) {
        setSizeCalled = true
        this.userSize = totalSize
        requestLayout()
    }

    private fun applyTotalSize() {
        if (userSize < 0)
            this.totalColumn = 0
        else if (userSize > maxColumn) {
            totalColumn = maxColumn
        } else totalColumn = userSize
    }
}