package com.example.tugaspraktikum3.utils;

import android.util.Log;

import com.example.tugaspraktikum3.R;
import com.example.tugaspraktikum3.models.Post;
import com.example.tugaspraktikum3.models.Story;
import com.example.tugaspraktikum3.models.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class DataSource {

    private static List<User> userList = new ArrayList<>();
    private static List<Post> feedPostList = new ArrayList<>();
    private static List<Post> profilePostList = new ArrayList<>();
    private static List<Story> storyList = new ArrayList<>();
    private static User currentUser;

    static {
        try {
            initUsers();
            initFeedPosts();
            initProfilePosts();
            initStories();
        } catch (Exception e) {
            Log.e("DataSource", "Error initializing data: " + e.getMessage());
        }
    }

    private static void initUsers() {
        currentUser = new User(1, "aalyaah._", R.drawable.profile_main, "Musliati", ";)", 9432, 93, 5
        );

        userList.add(new User(2, "rzkaaa.08", R.drawable.profile_2, "Rezka Wildan Nurhadi Bakri", "SI-UH23", 501, 404, 1));
        userList.add(new User(3, "sucizaitunn", R.drawable.profile_3, "Suci Zaitun", "H071231019", 909, 633, 1));
        userList.add(new User(4, "dianalifka", R.drawable.profile_4, "Dian Alifka", "huft", 517, 469, 1));
        userList.add(new User(5, "velvet.al", R.drawable.profile_5, "alyajugakk", "Apasih ", 645, 640, 1));
        userList.add(new User(6, "rifkykx", R.drawable.profile_6, "Rifky Kurniawan", "📹:@xy96gx", 486, 482,1));
        userList.add(new User(7, "zhrslsaa", R.drawable.profile_7, "zahra", "caca", 1094, 834, 1));
        userList.add(new User(8, "kvn.william", R.drawable.profile_8, "william", "Kupliq Best", 825, 856, 1));
        userList.add(new User(9, "mhrtlfth", R.drawable.profile_9, "Arta", "so long and goodnight", 793, 636, 1));
        userList.add(new User(10, "onlyrew", R.drawable.profile_10, "rezkaa", "so long and goodnight", 793, 636, 1));
    }
    private static void initFeedPosts() {
        if (userList.size() < 9) {
            Log.e("DataSource", "Error: userList has " + userList.size() + " items");
            return;
        }

        feedPostList.add(new Post(1, userList.get(0), R.drawable.post_1, "Productive Day! #apple #photography", 120, new Date(System.currentTimeMillis() - TimeUnit.HOURS.toMillis(2))));
        feedPostList.add(new Post(2, userList.get(1), R.drawable.post_3, "jangan Lupa ngopi", 85, new Date(System.currentTimeMillis() - TimeUnit.HOURS.toMillis(4))));
        feedPostList.add(new Post(3, userList.get(2), R.drawable.post_4, "Selamanya Bersama", 210, new Date(System.currentTimeMillis() - TimeUnit.HOURS.toMillis(6))));
        feedPostList.add(new Post(4, userList.get(3), R.drawable.post_5, "Birthday", 95, new Date(System.currentTimeMillis() - TimeUnit.HOURS.toMillis(8))));
        feedPostList.add(new Post(5, userList.get(4), R.drawable.post_6, "miyawww", 320, new Date(System.currentTimeMillis() - TimeUnit.HOURS.toMillis(10))));
        feedPostList.add(new Post(6, userList.get(5), R.drawable.post_7, "cobatimut", 75, new Date(System.currentTimeMillis() - TimeUnit.HOURS.toMillis(12))));
        feedPostList.add(new Post(7, userList.get(6), R.drawable.post_8, "gym hari ini", 430, new Date(System.currentTimeMillis() - TimeUnit.HOURS.toMillis(14))));
        feedPostList.add(new Post(8, userList.get(7), R.drawable.post_9, "ANJ", 185, new Date(System.currentTimeMillis() - TimeUnit.HOURS.toMillis(16))));
        feedPostList.add(new Post(9, userList.get(8), R.drawable.post_10, "kulyeah", 91, new Date(System.currentTimeMillis() - TimeUnit.HOURS.toMillis(18))));

    }

    private static void initProfilePosts() {
        profilePostList.add(new Post(101, currentUser, R.drawable.profile_post_1, "Foto 18/18", 350, new Date(System.currentTimeMillis() - TimeUnit.DAYS.toMillis(2))));
        profilePostList.add(new Post(102, currentUser, R.drawable.profile_post_2, "Foto 17/18", 280, new Date(System.currentTimeMillis() - TimeUnit.DAYS.toMillis(5))));
        profilePostList.add(new Post(103, currentUser, R.drawable.profile_post_3, "Foto 16/18", 190, new Date(System.currentTimeMillis() - TimeUnit.DAYS.toMillis(10))));
        profilePostList.add(new Post(104, currentUser, R.drawable.profile_post_4, "Foto 15/18", 415, new Date(System.currentTimeMillis() - TimeUnit.DAYS.toMillis(15))));
        profilePostList.add(new Post(105, currentUser, R.drawable.profile_post_5, "Foto 14/18", 320, new Date(System.currentTimeMillis() - TimeUnit.DAYS.toMillis(20))));
        profilePostList.add(new Post(106, currentUser, R.drawable.profile_post_6, "Foto 13/18", 456, new Date(System.currentTimeMillis() - TimeUnit.DAYS.toMillis(25))));
        profilePostList.add(new Post(107, currentUser, R.drawable.profile_post_7, "Foto 12/18", 412, new Date(System.currentTimeMillis() - TimeUnit.DAYS.toMillis(30))));
        profilePostList.add(new Post(108, currentUser, R.drawable.profile_post_8, "Foto 11/18", 875, new Date(System.currentTimeMillis() - TimeUnit.DAYS.toMillis(35))));
        profilePostList.add(new Post(109, currentUser, R.drawable.profile_post_9, "Foto 10/18", 123, new Date(System.currentTimeMillis() - TimeUnit.DAYS.toMillis(40))));
        currentUser.setPostsCount(profilePostList.size());
    }

    private static void initStories() {
        storyList.add(new Story(1, "hm", R.drawable.story_thumb_1, R.drawable.story_1, new Date(System.currentTimeMillis() - TimeUnit.DAYS.toMillis(200))));

    }

    public static List<User> getUserList() {
        return userList;
    }

    public static List<Post> getFeedPostList() {
        return feedPostList;
    }

    public static List<Post> getProfilePostList() {
        return profilePostList;
    }

    public static List<Story> getStoryList() {
        return storyList;
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void addPost(Post post) {
        profilePostList.add(0, post);
        feedPostList.add(0, post);
        currentUser.setPostsCount(profilePostList.size());
    }
    public static List<Post> getPostsByUser(User user) {
        List<Post> result = new ArrayList<>();
        Set<Integer> addedPostIds = new HashSet<>();

        for (Post post : feedPostList) {
            if (post.getUser().getId() == user.getId()) {
                result.add(post);
                addedPostIds.add(post.getId());
            }
        }

        if (user.getId() == currentUser.getId()) {
            for (Post post : profilePostList) {
                if (!addedPostIds.contains(post.getId())) {
                    result.add(post);
                    addedPostIds.add(post.getId());
                }
            }
        }

        return result;
    }
}