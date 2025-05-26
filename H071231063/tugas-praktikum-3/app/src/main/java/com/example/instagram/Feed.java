package com.example.instagram;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Feed implements Parcelable {

    String username, caption, name, bio, follower, following, like, comment, send, date;

    public String getDate() {
        return date;
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

    public void setSend(String send) {
        this.send = send;
    }

    int pfp, content;

    public String getFollower() {
        return follower;
    }


    public String getFollowing() {
        return following;
    }

    public Feed(String username, String name, String follower, String following, String bio, String like, String comment, String send,String caption, int pfp, int content, String date) {
        this.name = name;
        this.username = username;
        this.like = like;
        this.comment = comment;
        this.send = send;
        this.follower = follower;
        this.following = following;
        this.bio=bio;
        this.caption = caption;
        this.pfp = pfp;
        this.content = content;
        this.date = date;
    }
    public Feed(String username, String caption, int pfp, int content) {
        this.username = username;
        this.caption = caption;
        this.pfp = pfp;
        this.content = content;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getUsername() {
        return username;
    }

    public String getCaption() {
        return caption;
    }
    public String getBio() {
        return bio;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public int getPfp() {
        return pfp;
    }

    public int getContent() {
        return content;
    }

    public void setContent(int content) {
        this.content = content;
    }


    protected Feed(Parcel in) {
        username = in.readString();
        caption = in.readString();
        following = in.readString();
        follower = in.readString();
        pfp = in.readInt();
        content = in.readInt();
        like = in.readString();
        comment = in.readString();
        send = in.readString();
    }

    public static final Creator<Feed> CREATOR = new Creator<Feed>() {
        @Override
        public Feed createFromParcel(Parcel in) {
            return new Feed(in);
        }

        @Override
        public Feed[] newArray(int size) {
            return new Feed[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(username);
        dest.writeString(caption);
        dest.writeInt(pfp);
        dest.writeInt(content);
        dest.writeString(like);
        dest.writeString(comment);
        dest.writeString(send);
    }
}