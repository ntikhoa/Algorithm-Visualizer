package com.ntikhoa.algorithmvisualize

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View
import androidx.annotation.Nullable
import androidx.core.content.ContextCompat
import kotlin.properties.Delegates

class CustomView(context: Context, @Nullable attrs: AttributeSet) : View(context, attrs) {

    private var rect = Rect()
    private var paint = Paint(Paint.ANTI_ALIAS_FLAG)

    private var screenWidth by Delegates.notNull<Int>()
    private var screenHeight by Delegates.notNull<Int>()

    private val DEFAULT_POS_X = 0
    private val DEFAULT_WIDTH = 100

    init {
        screenWidth = context.resources.displayMetrics.widthPixels
        screenHeight = context.resources.displayMetrics.heightPixels

        paint.color = ContextCompat.getColor(getContext(),R.color.teal_700)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        rect.bottom = screenHeight
        rect.top = rect.bottom - DEFAULT_WIDTH
        rect.left = DEFAULT_POS_X
        rect.right = rect.left + DEFAULT_WIDTH

        canvas?.drawRect(rect, paint)
    }
}