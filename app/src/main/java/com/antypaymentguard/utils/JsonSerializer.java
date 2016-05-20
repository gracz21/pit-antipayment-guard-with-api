package com.antypaymentguard.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;

public class JsonSerializer<T> {

    public String serialize(T data) {
        final Gson gson = new GsonBuilder().excludeFieldsWithModifiers(Modifier.FINAL, Modifier.TRANSIENT, Modifier.STATIC).create();
        return gson.toJson(data);
    }

    public T deserialize(String data, Type clazzType) {
        final Gson gson = new GsonBuilder().excludeFieldsWithModifiers(Modifier.FINAL, Modifier.TRANSIENT, Modifier.STATIC).create();
        return gson.fromJson(data, clazzType);
    }

    public T deserialize(Reader data, Type clazzType) {
        final Gson gson = new GsonBuilder().excludeFieldsWithModifiers(Modifier.FINAL, Modifier.TRANSIENT, Modifier.STATIC).create();
        return gson.fromJson(data, clazzType);
    }

    public T deserialize(Reader data, Class<T> clazz) {
        final Gson gson = new GsonBuilder().excludeFieldsWithModifiers(Modifier.FINAL, Modifier.TRANSIENT, Modifier.STATIC).create();
        return gson.fromJson(data, clazz);
    }

    public T deserialize(String data, Type clazzType, String object) throws IOException {
        JsonParser jsonParser = new JsonParser();
        JsonElement jsonElement = jsonParser.parse(data);

        final Gson gson = new GsonBuilder().excludeFieldsWithModifiers(Modifier.FINAL, Modifier.TRANSIENT, Modifier.STATIC).create();

        if (jsonElement.isJsonObject()) {
            return gson.fromJson(jsonElement.getAsJsonObject().get(object), clazzType);

        }

        return null;
    }
}
