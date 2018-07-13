package com.foretree.text.support;

import com.twitter.text.Extractor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * 数据转换类
 * Created by silen on 13/07/2018
 */
public class TwitterHelper {
    public static List<TwitterModel> convert(List<Extractor.Entity> entities) {
        List<TwitterModel> models = new ArrayList<>();
        Iterator<Extractor.Entity> iterator = entities.iterator();
        while (iterator.hasNext()) {
            Extractor.Entity entity = iterator.next();
            models.add(new TwitterModel("", entity));
        }
        return models;
    }

    public static List<TwitterModel> convert(Extractor.Entity... entities) {
        List<TwitterModel> models = new ArrayList<>();
        for (Extractor.Entity entity : entities) {
            models.add(new TwitterModel("", entity));
        }
        return models;
    }

    public static TwitterModel convert(Extractor.Entity entity) {
        return new TwitterModel("", entity);
    }

    public static TwitterModel convert(String id, Extractor.Entity entity) {
        return new TwitterModel(id, entity);
    }
}
