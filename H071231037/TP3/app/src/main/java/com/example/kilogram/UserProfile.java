package com.example.kilogram;

public class UserProfile {
    private static UserProfile instance;

    private String userId;
    private String username;
    private String name;
    private String bio;
    private String link;
    private String profileImageUrl;
    private int postCount;
    private int followersCount;
    private int followingCount;

    // Private constructor for singleton pattern
    private UserProfile() {
        // Default values for current user
        this.userId = "current_user";
        this.username = "cirno";
        this.name = "c1rn9";
        this.bio = "Cirno is the Ice Fairy of the Lake and thus, in order to kill her, you'd probably have to remove the entire lake and warm it up there, and even then-";
        this.link = "https://static.wikia.nocookie.net/duckpond/images/b/b1/Cirnofumo.jpg/revision/latest/thumbnail/width/360/height/450?cb=20210727172305";
        this.profileImageUrl = "https://static.wikia.nocookie.net/duckpond/images/b/b1/Cirnofumo.jpg/revision/latest/thumbnail/width/360/height/450?cb=20210727172305";
        this.postCount = 6;
        this.followersCount = 520;
        this.followingCount = 480;
    }

    public static synchronized UserProfile getInstance() {
        if (instance == null) {
            instance = new UserProfile();
        }
        return instance;
    }

    // Getters and setters
    public String getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public int getPostCount() {
        return postCount;
    }

    public void setPostCount(int postCount) {
        this.postCount = postCount;
    }

    public int getFollowersCount() {
        return followersCount;
    }

    public void setFollowersCount(int followersCount) {
        this.followersCount = followersCount;
    }

    public int getFollowingCount() {
        return followingCount;
    }

    public void setFollowingCount(int followingCount) {
        this.followingCount = followingCount;
    }
}