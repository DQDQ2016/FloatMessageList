package com.dqdq.floatmessagelist

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Build
import android.os.Handler
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

    private var hideTime: Long = 5000 //每条item显示时间 单位:ms
    private var messageList = LinkedList<MessageItem>()
    private var fontSize = 45
    private var maxItemStrLayer = 2 // 每条item最多行数
    private var itemPaddingLeft = 30f
    private var itemMarginTop = 20
    private var itemLineSpec = 10
    private var fontColor = Color.WHITE

    constructor(ctx: Context) : super(ctx)
    @SuppressLint("Recycle")
    constructor(ctx: Context, attrs: AttributeSet) : super(ctx, attrs){
        var value = ctx.obtainStyledAttributes(attrs,R.styleable.FloatMessageList)
        fontColor = value.getColor(R.styleable.FloatMessageList_textColor,Color.WHITE)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?) {
        /// TODO: 12/4/2022  用户名颜色和消息颜色区分  消息消失淡出效果
        super.onDraw(canvas)
        var y = height
        var paint = TextPaint()
        paint.color = fontColor
        paint.textSize = fontSize.toFloat()
        var deleteList = LinkedList<MessageItem>()
        var now = System.currentTimeMillis()
        for (item in messageList.reversed()) {
            var layout = StaticLayout.Builder.obtain(
                item.msg,
                0, item.msg.length, paint, (width - itemPaddingLeft).toInt()
            )
                .setAlignment(Layout.Alignment.ALIGN_NORMAL)
                .setIncludePad(false)
                .setLineSpacing(1.0F, 1.0F)
                .setMaxLines(maxItemStrLayer)
                .setEllipsize(TextUtils.TruncateAt.END)
                .build()
            y -= layout.height + itemMarginTop
            if (y < 0 || now - item.sendTime >= hideTime){
                deleteList.add(item)
                continue
            }
            canvas!!.save()
            canvas.translate(itemPaddingLeft.toFloat(), y.toFloat())
            layout.draw(canvas)
            canvas.restore()
        }
        messageList.removeAll(deleteList)
        if (messageList.size>=0)
            handler.postDelayed({invalidate()},hideTime)
    }

    open fun pushMessage(msg: MessageItem) = messageList.add(msg).also { invalidate() }

    data class MessageItem(var msg: String,var sendTime: Long)
}