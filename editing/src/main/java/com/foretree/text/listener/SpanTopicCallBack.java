package com.foretree.text.listener;

import android.view.View;

import com.foretree.text.support.TwitterModel;

/**
 * 处理话题的回调
 * Created by shuyu on 2016/11/10.
 */

public interface SpanTopicCallBack {
    void onClick(View view, TwitterModel topicModel);
}
