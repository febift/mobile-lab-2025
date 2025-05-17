package com.example.praktikum_6;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class ApiResponse {

    @SerializedName("info")
    private Info info;

    @SerializedName("results")
    private List<Post> results;

    public Info getInfo() {
        return info;
    }

    public List<Post> getResults() {
        return results;
    }
}
