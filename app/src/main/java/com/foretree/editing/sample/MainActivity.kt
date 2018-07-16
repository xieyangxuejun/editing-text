package com.foretree.editing.sample


import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.foretree.text.EmojiManager
import com.foretree.text.RichEditBuilder
import com.foretree.text.support.TwitterModel
import com.foretree.text.listener.OnEditTextUtilJumpListener
import com.foretree.text.listener.SpanAtUserCallBack
import com.foretree.text.listener.SpanTopicCallBack
import com.foretree.text.listener.SpanUrlCallBack
import com.foretree.text.support.TwitterHelper
import com.twitter.text.Extractor
import kotlinx.android.synthetic.main.activity_main.*
import java.util.ArrayList


class MainActivity : AppCompatActivity() {
    val content = "日本热 @日本語ハッシュタグ 我是谢杨学君 @小罗 @123 #الجزائر @abc 你好呀.大帅哥 " +
            "@zhangshuai 来参加 #邓师傅partty #谢劳版外卖 走起 @长靖 吃鸡吃鸡0-0 url http://t.co" +
            " www.twitter.com/alibaba www.yahoo.co.jp t.co/blahblah www.poloshirts.uk.com"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val data = ArrayList<Int>()
        val strings = ArrayList<String>()
        for (i in 1..30) {
            val resId = resources.getIdentifier("e$i", "drawable", packageName)
            data.add(resId)
            strings.add("[e$i]")
        }
        EmojiManager.getInstance().addPatternAll(strings, data)
        setContentView(R.layout.activity_main)

        val spanUrlCallBack = object : SpanUrlCallBack {
            override fun phone(view: View, phone: String) {
                Toast.makeText(view.context, phone + " 被点击了", Toast.LENGTH_SHORT).show()
                if (view is TextView) {
                    view.highlightColor = Color.TRANSPARENT
                }
            }

            override fun url(view: View, url: String) {
                Toast.makeText(view.context, url + " 被点击了", Toast.LENGTH_SHORT).show()
                if (view is TextView) {
                    view.highlightColor = Color.TRANSPARENT
                }
            }
        }

        val spanAtUserCallBack = object : SpanAtUserCallBack {
            override fun onClick(view: View, userModel1: TwitterModel) {
                Toast.makeText(view.context, userModel1.value + " 被点击了", Toast.LENGTH_SHORT).show()
                if (view is TextView) {
                    view.highlightColor = Color.TRANSPARENT
                }
            }
        }

        val spanTopicCallBack = object : SpanTopicCallBack {
            override fun onClick(view: View, topicModel: TwitterModel) {
                Toast.makeText(view.context, topicModel.value + " 被点击了", Toast.LENGTH_SHORT).show()
                if (view is TextView) {
                    view.highlightColor = Color.TRANSPARENT
                }
            }
        }

        Extractor().apply {
            //            val richTextBuilder = RichTextBuilder(applicationContext)
//            richTextBuilder.setContent(content)
//                    .setAtColor(Color.RED)
//                    .setLinkColor(Color.BLUE)
//                    .setTopicColor(Color.YELLOW)
//                    .setTextView(et_input)
//                    .setListUser(this.extractMentionsOrListsWithIndices(content))
//                    .setListTopic(this.extractHashtagsWithIndices(content))
//                    .setNeedUrl(true)
//                    .setNeedNum(true)
//                    .setEmojiSize(20)
//                    //.setVerticalAlignment(CenteredImageSpan.ALIGN_CENTER)
//                    .setSpanAtUserCallBack(spanAtUserCallBack)
//                    .setSpanUrlCallBack(spanUrlCallBack)
//                    .setSpanTopicCallBack(spanTopicCallBack)
//                    //自定义span，如果不需要可不设置
//                    //.setSpanCreateListener(spanCreateListener)
//                    .build()
            val richEditBuilder = RichEditBuilder()
            val hashModels = this.extractHashtagsWithIndices(content)
            val cashModels = this.extractMentionsOrListsWithIndices(content)
            richEditBuilder
                    .setEditText(et_input)
//                    .setTopicModels(TwitterHelper.convert(hashModels))
//                    .setUserModels(TwitterHelper.convert(cashModels))
                    .setColorAtUser("#FF0000")
                    .setColorTopic("#0000FF")
                    .setEditTextAtUtilJumpListener(object : OnEditTextUtilJumpListener {
                        override fun notifyAt() {
                            Toast.makeText(applicationContext, "notify", Toast.LENGTH_SHORT).show()
                        }

                        override fun notifyTopic() {
                            Toast.makeText(applicationContext, "notifyTopic", Toast.LENGTH_SHORT).show()
                        }
                    })
                    .builder()

            et_input.resolveAtResult(TwitterHelper.convert(cashModels[0]))
            et_input.resolveAtResult(TwitterHelper.convert(cashModels[1]))
            et_input.resolveTopicResult(TwitterHelper.convert(hashModels[0]))
            et_input.resolveTopicResult(TwitterHelper.convert(hashModels[1]))

            emoji_layout.editTextSmile = et_input

        }
    }
}
