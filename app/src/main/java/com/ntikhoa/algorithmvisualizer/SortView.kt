package com.ntikhoa.algorithmvisualizer

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import androidx.annotation.Nullable
import androidx.core.content.ContextCompat
import android.view.MotionEvent
import kotlinx.coroutines.*


class SortView(context: Context, @Nullable attrs: AttributeSet) : View(context, attrs) {

    private val coroutineScope = CoroutineScope(Dispatchers.Main.immediate)

    private var setSizeCalled = false
    private var getRandomValueCalled = false
    private var onSortCalled = false
    private var downTouch = false

    private var DEFAULT_COLUMN = 20
    private val MARGIN = 10f
    private val MIN_WIDTH = 10f


    private var paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var highLightedPaint = Paint(Paint.ANTI_ALIAS_FLAG)

    private var screenWidth = 0f
    private var screenHeight = 0f

    private var maxColumn = 0

    private var firstColumnPos = 0f

    private var totalColumn = DEFAULT_COLUMN
    private var columnWidth = 0f
    private var heightWeight = 0f

    private var listener: OnSortListener? = null

    private lateinit var randomValues: ArrayList<Int>

    private var highlightedFirst = -1
    private var highlightedSecond = -1

    init {
        paint.color = ContextCompat.getColor(context, R.color.teal_700)
        highLightedPaint.color = Color.RED
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        calculateAttribute()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        calculateAttribute()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        if (!getRandomValueCalled) {
            randomValues = getRandomValue()
        }
        for (i in 0 until totalColumn) {
            drawSquare(canvas, i, randomValues[i])
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

    private fun getMaxColumn(): Int {
        return ((screenWidth + MARGIN) / (MIN_WIDTH + MARGIN)).toInt()
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

    private fun drawSquare(canvas: Canvas?, index: Int, value: Int) {
        val rect = RectF()
        rect.bottom = screenHeight
        rect.top = rect.bottom - (screenHeight - heightWeight * (totalColumn - 1 - value))
        rect.left = firstColumnPos + index * (columnWidth + MARGIN)
        rect.right = rect.left + columnWidth

        if (index == highlightedFirst || index == highlightedSecond)
            canvas?.drawRect(rect, highLightedPaint)
        else canvas?.drawRect(rect, paint)
    }

    private var userSize = 0

    private fun applyTotalSize() {
        if (userSize < 0)
            this.totalColumn = 0
        else if (userSize > maxColumn) {
            totalColumn = maxColumn
        } else totalColumn = userSize
    }

    private fun getRandomValue(): ArrayList<Int> {
        getRandomValueCalled = true
        val list = (0 until totalColumn).toMutableList()
        list.shuffle()
        return ArrayList(list)
    }

    suspend fun swap(firstIndex: Int, secondIndex: Int) {
        withContext(Dispatchers.Main) {
            onStartSwap(firstIndex, secondIndex)
            onSwap(firstIndex, secondIndex)
        }
    }

    private suspend fun onStartSwap(firstIndex: Int, secondIndex: Int) {
        highlightedFirst = firstIndex
        highlightedSecond = secondIndex
        invalidate()
        delay(10)
    }

    private suspend fun onSwap(firstIndex: Int, secondIndex: Int) {
        val temp = randomValues[firstIndex]
        randomValues[firstIndex] = randomValues[secondIndex]
        randomValues[secondIndex] = temp
        invalidate()
        delay(100)
    }

    fun setOnSortListener(listener: OnSortListener) {
        this.listener = listener
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        super.onTouchEvent(event)
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                downTouch = true
                return true
            }
            MotionEvent.ACTION_UP -> {
                if (downTouch) {
                    downTouch = false
                    performClick()
                    return true
                }
            }
        }
        return false
    }

    override fun performClick(): Boolean {
        start()
        return true
    }

    fun start() {
        if (!onSortCalled) {
            println("performClick Called")
            onSortCalled = true
            coroutineScope.launch {
                try {
                    if (!checkIfSorted()) {
                        println("Sorting")
                        listener?.onSort(randomValues)
                        highlightedFirst = -1
                        highlightedSecond = -1
                        invalidate()
                    }
                    println("Sorted")
                    onSortCalled = false
                } catch(e: CancellationException) {
                    highlightedFirst = -1
                    highlightedSecond = -1
                    invalidate()
                }
            }
        }
    }

    fun cancel() {
        coroutineScope.coroutineContext.cancelChildren()
    }

    fun reset() {
        cancel()
        getRandomValueCalled = false
        onSortCalled = false
        invalidate()
    }

    fun setTotalSize(totalSize: Int) {
        setSizeCalled = true
        this.userSize = totalSize
        requestLayout()
    }

    private fun checkIfSorted(): Boolean {
        for (i in 0 until randomValues.size - 1) {
            if (randomValues[i] > randomValues[i + 1])
                return false
        }
        return true
    }

    interface OnSortListener {
        suspend fun onSort(array: List<Int>)
    }
}