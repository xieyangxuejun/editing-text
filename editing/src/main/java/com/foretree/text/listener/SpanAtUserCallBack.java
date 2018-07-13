package com.foretree.text.listener;

import android.view.View;

import com.foretree.text.support.TwitterModel;

/**
 * 处理@被某人的回调
 * Created by shuyu on 2016/11/10.
 */

public interface SpanAtUserCallBack {
    void onClick(View view, TwitterModel userModel1);
}
