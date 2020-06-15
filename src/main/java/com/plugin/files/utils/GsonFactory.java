package com.plugin.files.utils;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Map;

public class GsonFactory {
    public Gson create() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(new TypeToken<Map<String, Object>>() {}.getType(), new MapDeserializerDoubleAsIntFix());
        gsonBuilder.setPrettyPrinting();
        return gsonBuilder.create();
    }
}