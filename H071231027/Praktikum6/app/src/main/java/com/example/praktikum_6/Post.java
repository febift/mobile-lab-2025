package com.example.praktikum_6;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class Post {

    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("status")
    private String status;

    @SerializedName("species")
    private String species;

    @SerializedName("type")
    private String type;

    @SerializedName("gender")
    private String gender;

    @SerializedName("origin")
    private Location origin;

    @SerializedName("location")
    private Location location;

    @SerializedName("image")
    private String image;

    @SerializedName("episode")
    private List<String> episode;

    @SerializedName("url")
    private String url;

    @SerializedName("created")
    private String created;

    // Getter methods
    public int getId() { return id; }
    public String getName() { return name; }
    public String getStatus() { return status; }
    public String getSpecies() { return species; }
    public String getType() { return type; }
    public String getGender() { return gender; }
    public Location getOrigin() { return origin; }
    public Location getLocation() { return location; }
    public String getImage() { return image; }
    public List<String> getEpisode() { return episode; }
    public String getUrl() { return url; }
    public String getCreated() { return created; }
}
