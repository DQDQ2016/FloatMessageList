package com.dqdq.floatmessagelist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {

    var floatMessageList: FloatMessageList? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var strList = mutableListOf(
            "紫电霸王龙: 发送这些的意义是什么，网络的弄潮儿。精神的满足以及胜利，你是APEX捍卫者，了解你的捍卫者",
            "dqdq1: 我发送一条消息，真的就会这样吗？难道你说的是真的吗？人生是大家哦",
            "天才卡狗: 想~吃~肉~肉~",
            "动力小子: 再快点再快点再快点！！！！！！ ",
            "2031身上: 本我 超我  真我，国富论  资本论  战争与和平  想象的共同体  利维坦  诺贝尔文学奖",
            "测试发送：新增一条消息,那又会怎么样，这一切会改变吗"
        )
        floatMessageList = findViewById(R.id.float_msg)
        findViewById<Button>(R.id.btn_msg).setOnClickListener {
            floatMessageList?.pushMessage(
                FloatMessageList.MessageItem(strList[(0..5).random()],System.currentTimeMillis())
            )
        }
    }
}