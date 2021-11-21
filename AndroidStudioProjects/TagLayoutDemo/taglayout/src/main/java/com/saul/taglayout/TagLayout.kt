package com.saul.taglayout

import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import android.view.ViewGroup
import androidx.core.view.children
import kotlin.math.max

class TagLayout(context: Context, attrs: AttributeSet) : ViewGroup(context, attrs) {

    private val childrenBounds = mutableListOf<Rect>()
    private var horizontalSpace = 0f
    private var verticalSpace = 0f

    init {
        val ta = context.obtainStyledAttributes(attrs, R.styleable.TagLayout)
        horizontalSpace = ta.getDimension(R.styleable.TagLayout_horizontalSpace,0f)
        verticalSpace = ta.getDimension(R.styleable.TagLayout_horizontalSpace,0f)
    }

    override fun generateLayoutParams(attrs: AttributeSet?): LayoutParams {
        return MarginLayoutParams(context, attrs)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val widthSpecMode = MeasureSpec.getMode(widthMeasureSpec)
        val widthSpecSize = MeasureSpec.getSize(widthMeasureSpec)
        var widthUsed = 0
        var heightUsed = 0
        var lineMaxHeight = 0
        var lineWidthUsed = 0

        children.forEachIndexed { index, child ->
            if (child.layoutParams is MarginLayoutParams){
                measureChildWithMargins(child, widthMeasureSpec, 0, heightMeasureSpec, heightUsed)
            } else {
                child.layoutParams = MarginLayoutParams(child.layoutParams.width,child.layoutParams.height)
                measureChildWithMargins(child, widthMeasureSpec, 0, heightMeasureSpec, heightUsed)
            }

            if (widthSpecMode != MeasureSpec.UNSPECIFIED && lineWidthUsed + child.measuredWidth > widthSpecSize) {
                lineWidthUsed = 0
                heightUsed += (lineMaxHeight + verticalSpace).toInt()
                lineMaxHeight = 0
                measureChildWithMargins(child, widthMeasureSpec, 0, heightMeasureSpec, heightUsed)
            }

            if (index >= childrenBounds.size) {
                childrenBounds.add(Rect())
            }

            val bounds = childrenBounds[index]
            bounds.set(
                lineWidthUsed,
                heightUsed,
                lineWidthUsed + child.measuredWidth,
                heightUsed + child.measuredHeight
            )

            lineWidthUsed += (child.measuredWidth + horizontalSpace).toInt()
            widthUsed = max(lineWidthUsed, widthUsed)
            lineMaxHeight = max(lineMaxHeight, child.measuredHeight)
        }

        val selfWidth = widthUsed
        val selfHeight = heightUsed + lineMaxHeight
        setMeasuredDimension(selfWidth, selfHeight)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        children.forEachIndexed { index, child ->
            val childRect = childrenBounds[index]
            child.layout(childRect.left, childRect.top, childRect.right, childRect.bottom)
        }
    }
}