package com.dqdq.floatmessagelist

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Build
import android.text.Layout
import android.text.StaticLayout
import android.text.TextPaint
import android.text.TextUtils
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.core.view.marginLeft
import java.util.*
import kotlin.collections.LinkedHashSet

/**
 * Created by DQDQ on 3/4/2022.
 */
class FloatMessageList : View {

    var hideTime: Long = 1000 //每条item显示时间 单位:ms
    var itemHight = 40
    var itemWidth = 0
    var messageList = LinkedList<String>()
    var fontSize = 45
    var maxItemStrLayer = 2 // 每条item最多行数
    var itemPaddingLeft = 15f
    var itemMarginTop = 20
    var itemLineSpec = 10

    constructor(ctx: Context) : super(ctx)
    constructor(ctx: Context, attrs: AttributeSet) : super(ctx, attrs)


    @RequiresApi(Build.VERSION_CODES.M)
    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        var y = height
        var paint = TextPaint()
        paint.color = Color.WHITE
        paint.textSize = fontSize.toFloat()
        for (str in messageList.reversed()) {

            var layout = StaticLayout.Builder.obtain(
                str,
                0, str.length, paint, (width - itemPaddingLeft).toInt()
            )
                .setAlignment(Layout.Alignment.ALIGN_NORMAL)
                .setIncludePad(false)
                .setLineSpacing(1.0F, 1.0F)
                .setMaxLines(maxItemStrLayer)
                .setEllipsize(TextUtils.TruncateAt.END)
                .build()
            y -= layout.height + itemMarginTop
            if (y < 0)
                break
            Log.i("test", "y: $y")
            canvas!!.save()
            canvas.translate(itemPaddingLeft.toFloat(), y.toFloat())
            layout.draw(canvas)
            canvas.restore()
        }

    }

    open fun pushMessage(str: String) = messageList.add(str).also { invalidate() }

}