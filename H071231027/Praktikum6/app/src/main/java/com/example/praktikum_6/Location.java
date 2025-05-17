package com.example.praktikum_6;

import com.google.gson.annotations.SerializedName;

public class Location {
    @SerializedName("name")
    private String name;

    @SerializedName("url")
    private String url;

    public String getName() { return name; }
    public String getUrl() { return url; }
}
