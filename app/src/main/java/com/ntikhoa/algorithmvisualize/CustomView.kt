package com.ntikhoa.algorithmvisualize

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import androidx.annotation.Nullable
import androidx.core.content.ContextCompat
import kotlin.properties.Delegates

class CustomView(context: Context, @Nullable attrs: AttributeSet) : View(context, attrs) {

    private var paint = Paint(Paint.ANTI_ALIAS_FLAG)

    private var screenWidth by Delegates.notNull<Int>()
    private var screenHeight by Delegates.notNull<Int>()

    private val DEFAULT_POS_X = 0
    private var DEFAULT_WIDTH = 20f

    private val HEIGHT_OFFSET = 50

    private val totalColumn = 20

    private val margin = 10

    init {
        screenWidth = context.resources.displayMetrics.widthPixels
        screenHeight = context.resources.displayMetrics.heightPixels

        paint.color = ContextCompat.getColor(context, R.color.teal_700)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        DEFAULT_WIDTH = (screenWidth - margin * totalColumn) / 20f

        for (i in 0..totalColumn) {
            drawSquare(canvas, i)
        }
    }

    private fun drawSquare(canvas: Canvas?, index: Int) {
        val rect = RectF()
        rect.bottom = screenHeight.toFloat()
        rect.top = rect.bottom - (index + 1) * HEIGHT_OFFSET
        rect.left = (DEFAULT_POS_X + index * (DEFAULT_WIDTH + margin))
        rect.right = rect.left + DEFAULT_WIDTH

        if (index == 0) {
            rect.left += margin / 2
        }

        canvas?.drawRect(rect, paint)
    }
}