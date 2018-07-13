package com.foretree.text.listener;

import android.content.Context;

import com.foretree.text.support.TwitterModel;
import com.foretree.text.span.ClickAtUserSpan;
import com.foretree.text.span.ClickTopicSpan;
import com.foretree.text.span.LinkSpan;

/**
 * Created by guoshuyu on 2017/8/31.
 */

public interface SpanCreateListener {

    ClickAtUserSpan getCustomClickAtUserSpan(Context context, TwitterModel userModel, int color, SpanAtUserCallBack spanClickCallBack);

    ClickTopicSpan getCustomClickTopicSpan(Context context, TwitterModel topicModel, int color, SpanTopicCallBack spanTopicCallBack);

    LinkSpan getCustomLinkSpan(Context context, String url, int color, SpanUrlCallBack spanUrlCallBack);
}
