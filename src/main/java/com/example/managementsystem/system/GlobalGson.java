package com.example.managementsystem.system;

import com.google.gson.Gson;

//JSON mapper
public class GlobalGson {

    private Gson gson = new Gson();

    private static final GlobalGson globalGson = new GlobalGson();

    public static GlobalGson get() {
        return globalGson;
    }

    private GlobalGson() {

    }

    public Gson getGson() {
        if (gson == null) {
            gson = new Gson();
        }
        return gson;
    }
}
