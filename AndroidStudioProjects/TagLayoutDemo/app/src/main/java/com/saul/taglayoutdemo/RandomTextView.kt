package com.saul.taglayoutdemo

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import kotlin.random.Random

class RandomTextView(context: Context, attrs: AttributeSet?) : AppCompatTextView(context, attrs) {

    private val texts = mutableListOf("好一条二哈把我风头给抢了","二次元","你不知道的事","体育","游戏","动漫","王一元","哪里都是你","黑色毛衣")
    private val colors = intArrayOf(R.color.purple_200,R.color.purple_500,R.color.purple_700,R.color.teal_200,R.color.teal_700,R.color.black)
    private val sizes = floatArrayOf(4.dp,6.dp,7.dp,8.dp,10.dp,13.dp)
    private val verticalPadding = 15
    private val horizontalPadding = 25

    constructor(context: Context) : this(context,null)

    init {
        setBackgroundColor(resources.getColor(colors[Random.nextInt(0, colors.size - 1)]))
        text = texts[Random.nextInt(0, texts.size - 1)]
        setTextColor(resources.getColor(R.color.white))
        textSize = sizes[Random.nextInt(0, sizes.size - 1)]
        setPadding(horizontalPadding, verticalPadding, horizontalPadding, verticalPadding)
    }
}