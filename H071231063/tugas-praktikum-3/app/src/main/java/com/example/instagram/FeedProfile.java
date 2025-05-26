package com.example.instagram;

public class FeedProfile {

    public void setCaption(String caption) {
        this.caption = caption;
    }

    private String imageUri;

    private String caption;
    private String like;
    private String comment;

    private String send;



    public FeedProfile(String imageUri, String caption, String like, String comment, String send) {
        this.imageUri = imageUri;
        this.caption = caption;
        this.like = like;
        this.comment = comment;
        this.send = send;
    }

    public String getImageUri() {
        return imageUri;
    }

    public String getCaption() {
        return caption;
    }

    public String getLike() {
        return like;
    }

    public String getComment() {
        return comment;
    }

    public String getSend() {
        return send;
    }

}
