package com.example.instagram;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Story implements Parcelable {
    String storyTitle;
    int storyCover;

    public Story(String storyTitle, int storyCover) {
        this.storyTitle = storyTitle;
        this.storyCover = storyCover;
    }

    public String getStoryTitle() {
        return storyTitle;
    }

    public int getStoryCover() {
        return storyCover;
    }


    protected Story(Parcel in) {
        storyTitle = in.readString();
        storyCover = in.readInt();
    }

    public static final Creator<Story> CREATOR = new Creator<Story>() {
        @Override
        public Story createFromParcel(Parcel in) {
            return new Story(in);
        }

        @Override
        public Story[] newArray(int size) {
            return new Story[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(storyTitle);
        dest.writeInt(storyCover);
    }
}
