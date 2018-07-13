package com.foretree.text.listener;

import android.content.Context;
import android.text.method.MovementMethod;

import com.foretree.text.support.TwitterModel;
import com.foretree.text.span.ClickAtUserSpan;
import com.foretree.text.span.ClickTopicSpan;
import com.foretree.text.span.LinkSpan;

/**
 * textview 显示接口
 * Created by guoshuyu on 2017/8/22.
 */

public interface ITextViewShow {
    void setText(CharSequence charSequence);

    CharSequence getText();

    void setMovementMethod(MovementMethod movementMethod);

    void setAutoLinkMask(int flag);

    ClickAtUserSpan getCustomClickAtUserSpan(Context context, TwitterModel userModel, int color, SpanAtUserCallBack spanClickCallBack);

    ClickTopicSpan getCustomClickTopicSpan(Context context, TwitterModel topicModel, int color, SpanTopicCallBack spanTopicCallBack);

    LinkSpan getCustomLinkSpan(Context context, String url, int color, SpanUrlCallBack spanUrlCallBack);

    int emojiSize();

    int verticalAlignment();
}
