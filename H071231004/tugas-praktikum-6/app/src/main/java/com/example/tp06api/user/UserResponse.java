package com.example.tp06api.user;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserResponse {

    @SerializedName("results")
    private List<User> results;

    public List<User> getResults() {
        return results;
    }
}