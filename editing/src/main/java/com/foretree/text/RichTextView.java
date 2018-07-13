package com.foretree.text;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.text.DynamicLayout;
import android.text.StaticLayout;
import android.text.style.DynamicDrawableSpan;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.foretree.text.listener.SpanAtUserCallBack;
import com.foretree.text.listener.SpanCreateListener;
import com.foretree.text.listener.SpanTopicCallBack;
import com.foretree.text.listener.SpanUrlCallBack;
import com.foretree.text.support.TwitterModel;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by guoshuyu on 2017/8/28.
 */

@SuppressLint("AppCompatCustomView")
public class RichTextView extends TextView {

    private List<TwitterModel> topicList = new ArrayList<>();

    private List<TwitterModel> nameList = new ArrayList<>();

    private int atColor = Color.BLUE;

    private int topicColor = Color.BLUE;

    private int linkColor = Color.BLUE;

    private int emojiSize = 0;

    private SpanUrlCallBack spanUrlCallBackListener;

    private SpanAtUserCallBack spanAtUserCallBackListener;

    private SpanTopicCallBack spanTopicCallBackListener;

    private SpanCreateListener spanCreateListener;

    private boolean needNumberShow = true;//是否需要数字处理

    private boolean needUrlShow = true;//是否需要url处理

    private int emojiVerticalAlignment = DynamicDrawableSpan.ALIGN_BOTTOM;//垂直方式

    public RichTextView(Context context) {
        super(context);
        init(context, null);
    }

    public RichTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public RichTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        if (isInEditMode())
            return;

        if (attrs != null) {
            TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.RichTextView);
            needNumberShow = array.getBoolean(R.styleable.RichTextView_needNumberShow, false);
            needUrlShow = array.getBoolean(R.styleable.RichTextView_needUrlShow, false);
            atColor = array.getColor(R.styleable.RichTextView_atColor, Color.BLUE);
            topicColor = array.getColor(R.styleable.RichTextView_topicColor, Color.BLUE);
            linkColor = array.getColor(R.styleable.RichTextView_linkColor, Color.BLUE);
            emojiSize = array.getInteger(R.styleable.RichTextView_emojiSize, 0);
            emojiVerticalAlignment = array.getInteger(R.styleable.RichTextView_emojiVerticalAlignment, DynamicDrawableSpan.ALIGN_BOTTOM);
            array.recycle();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        StaticLayout layout = null;
        Field field = null;
        try {
            Field staticField = DynamicLayout.class.getDeclaredField("sStaticLayout");
            staticField.setAccessible(true);
            layout = (StaticLayout) staticField.get(DynamicLayout.class);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        if (layout != null) {
            try {
                field = StaticLayout.class.getDeclaredField("mMaximumVisibleLineCount");
                field.setAccessible(true);
                field.setInt(layout, getMaxLines());
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (layout != null && field != null) {
            try {
                field.setInt(layout, Integer.MAX_VALUE);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    private SpanUrlCallBack spanUrlCallBack = new SpanUrlCallBack() {
        @Override
        public void phone(View view, String phone) {
            if (spanUrlCallBackListener != null) {
                spanUrlCallBackListener.phone(view, phone);
            }
        }

        @Override
        public void url(View view, String url) {
            if (spanUrlCallBackListener != null) {
                spanUrlCallBackListener.url(view, url);
            }
        }
    };

    private SpanAtUserCallBack spanAtUserCallBack = new SpanAtUserCallBack() {
        @Override
        public void onClick(View view, TwitterModel userModel1) {
            if (spanAtUserCallBackListener != null) {
                spanAtUserCallBackListener.onClick(view, userModel1);
            }
        }
    };

    private SpanTopicCallBack spanTopicCallBack = new SpanTopicCallBack() {
        @Override
        public void onClick(View view, TwitterModel topicModel) {
            if (spanTopicCallBackListener != null) {
                spanTopicCallBackListener.onClick(view, topicModel);
            }
        }
    };

    /**
     * 显示处理文本
     *
     * @param content
     */
    private void resolveRichShow(String content) {
        RichTextBuilder richTextBuilder = new RichTextBuilder(getContext());
        richTextBuilder.setContent(content)
                .setAtColor(atColor)
                .setLinkColor(linkColor)
                .setTopicColor(topicColor)
                .setListUser(nameList)
                .setListTopic(topicList)
                .setNeedNum(needNumberShow)
                .setNeedUrl(needUrlShow)
                .setTextView(this)
                .setEmojiSize(emojiSize)
                .setSpanAtUserCallBack(spanAtUserCallBack)
                .setSpanUrlCallBack(spanUrlCallBack)
                .setSpanTopicCallBack(spanTopicCallBack)
                .setVerticalAlignment(emojiVerticalAlignment)
                .setSpanCreateListener(spanCreateListener)
                .build();

    }

    /**
     * 设置@某人文本
     *
     * @param text     文本
     * @param nameList @人列表
     */
    public void setRichTextUser(String text, List<TwitterModel> nameList) {
        this.setRichText(text, nameList, topicList);
    }

    /**
     * 设置话题文本
     *
     * @param text      文本
     * @param topicList 话题列表
     */
    public void setRichTextTopic(String text, List<TwitterModel> topicList) {
        this.setRichText(text, nameList, topicList);
    }

    /**
     * 设置话题和@文本
     *
     * @param text      文本
     * @param nameList  @人列表
     * @param topicList 话题列表
     */
    public void setRichText(String text, List<TwitterModel> nameList, List<TwitterModel> topicList) {
        if (nameList != null) {
            this.nameList = nameList;
        }
        if (topicList != null) {
            this.topicList = topicList;
        }
        resolveRichShow(text);
    }

    /**
     * 设置话题和@文本
     *
     * @param text 文本
     */
    public void setRichText(String text) {
        setRichText(text, nameList, topicList);
    }

    public boolean isNeedNumberShow() {
        return needNumberShow;
    }

    public List<TwitterModel> getTopicList() {
        return topicList;
    }

    /**
     * 设置话题列表
     */
    public void setTopicList(List<TwitterModel> topicList) {
        this.topicList = topicList;
    }

    public List<TwitterModel> getNameList() {
        return nameList;
    }

    /**
     * 设置at列表
     */
    public void setNameList(List<TwitterModel> nameList) {
        this.nameList = nameList;
    }

    /**
     * 是否需要处理数字
     *
     * @param needNumberShow 是否需要高亮数字和点击
     */
    public void setNeedNumberShow(boolean needNumberShow) {
        this.needNumberShow = needNumberShow;
    }

    public boolean isNeedUrlShow() {
        return needUrlShow;
    }

    /**
     * 是否需要处理数字
     *
     * @param needUrlShow 是否需要高亮url和点击
     */
    public void setNeedUrlShow(boolean needUrlShow) {
        this.needUrlShow = needUrlShow;
    }

    /**
     * url点击
     */
    public void setSpanUrlCallBackListener(SpanUrlCallBack spanUrlCallBackListener) {
        this.spanUrlCallBackListener = spanUrlCallBackListener;
    }

    /**
     * at某人点击
     */
    public void setSpanAtUserCallBackListener(SpanAtUserCallBack spanAtUserCallBackListener) {
        this.spanAtUserCallBackListener = spanAtUserCallBackListener;
    }

    /**
     * 设置自定义span回调
     */
    public void setSpanCreateListener(SpanCreateListener spanCreateListener) {
        this.spanCreateListener = spanCreateListener;
    }

    /**
     * 话题点击
     */
    public void setSpanTopicCallBackListener(SpanTopicCallBack spanTopicCallBackListener) {
        this.spanTopicCallBackListener = spanTopicCallBackListener;
    }

    public int getAtColor() {
        return atColor;
    }

    /**
     * at某人颜色
     */
    public void setAtColor(int atColor) {
        this.atColor = atColor;
    }

    public int getTopicColor() {
        return topicColor;
    }

    /**
     * 话题颜色
     */
    public void setTopicColor(int topicColor) {
        this.topicColor = topicColor;
    }

    public int getLinkColor() {
        return linkColor;
    }

    /**
     * 链接颜色
     */
    public void setLinkColor(int linkColor) {
        this.linkColor = linkColor;
    }


    public void setEmojiSize(int emojiSize) {
        this.emojiSize = emojiSize;
    }


    /**
     * emoji垂直
     */
    public void setEmojiVerticalAlignment(int emojiVerticalAlignment) {
        this.emojiVerticalAlignment = emojiVerticalAlignment;
    }

    public int getEmojiVerticalAlignment() {
        return emojiVerticalAlignment;
    }

}
