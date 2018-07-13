package com.foretree.text.support;

import com.twitter.text.Extractor;

import java.io.Serializable;

/**
 * 适配文字
 * Created by silen on 13/07/2018
 */
public class TwitterModel implements Serializable {

    private static final long serialVersionUID = -6457093238322024288L;

    protected String id;
    protected String value;
    protected Extractor.Entity entity;
    protected Extractor.Entity.Type type;

    public TwitterModel() {
    }

    public TwitterModel(String id, Extractor.Entity entity) {
        this.id = id;
        this.entity = entity;
        this.value = entity.getValue();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Extractor.Entity getEntity() {
        return entity;
    }

    public void setEntity(Extractor.Entity entity) {
        this.entity = entity;
    }

    public Extractor.Entity.Type getType() {
        return type = entity.getType();
    }

    public static TwitterModel resolveModel(TwitterModel model) {
        TwitterModel m = new TwitterModel(model.id, model.entity);
        String reValue = m.value;
        switch (model.getType()) {
            case HASHTAG:               //topic
                reValue = "#" + model.value + "#";
                break;
            case MENTION:               //alt
                reValue = "@" + model.value;
                break;
            default:
                break;
        }
        m.setValue(reValue);
        return m;
    }

    public static TwitterModel realModel(TwitterModel model) {
        TwitterModel m = new TwitterModel(model.id, model.entity);
        String reValue = m.value;
        switch (model.getType()) {
            case HASHTAG:               //topic
                reValue = model.value.replace("#", "").replace("#", "");
                break;
            case MENTION:               //alt
                reValue = model.value.replace("@", "").replace("\b", "");
                break;
            default:
                break;
        }
        m.setValue(reValue);
        return m;
    }
}
