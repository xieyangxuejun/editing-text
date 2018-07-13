package com.foretree.text;


import com.foretree.text.listener.OnEditTextUtilJumpListener;
import com.foretree.text.support.TwitterModel;

import java.util.List;

/**
 * 富文本设置 话题、at某人，链接识别
 * Created by guoshuyu on 2017/8/18.
 */

public class RichEditBuilder {

    private RichEditText editText;

    private List<TwitterModel> userModels;

    private List<TwitterModel> topicModels;

    private OnEditTextUtilJumpListener editTextAtUtilJumpListener;

    /**
     * At颜色
     */
    private String colorTopic = "#0000FF";
    /**
     * 话题颜色
     */
    private String colorAtUser = "#f77521";

    /**
     * 输入框
     */
    public RichEditBuilder setEditText(RichEditText editText) {
        this.editText = editText;
        return this;
    }

    /**
     * at列表
     */
    public RichEditBuilder setUserModels(List<TwitterModel> userModels) {
        this.userModels = userModels;
        return this;
    }

    /**
     * 话题列表
     */
    public RichEditBuilder setTopicModels(List<TwitterModel> topicModels) {
        this.topicModels = topicModels;
        return this;
    }

    /**
     * 输入监听回调
     */
    public RichEditBuilder setEditTextAtUtilJumpListener(OnEditTextUtilJumpListener editTextAtUtilJumpListener) {
        this.editTextAtUtilJumpListener = editTextAtUtilJumpListener;
        return this;
    }

    /**
     * 话题颜色
     */
    public RichEditBuilder setColorTopic(String colorTopic) {
        this.colorTopic = colorTopic;
        return this;
    }

    /**
     * at颜色
     */
    public RichEditBuilder setColorAtUser(String colorAtUser) {
        this.colorAtUser = colorAtUser;
        return this;
    }

    public RichEditText builder() {
        editText.setEditTextAtUtilJumpListener(editTextAtUtilJumpListener);
        editText.setModelList(userModels, topicModels);
        editText.setColorAtUser(colorAtUser);
        editText.setColorTopic(colorTopic);
        return editText;
    }

}
