package com.example.library_comic.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class Chapter implements Parcelable {

    private String title;
    private String hrefUrl;
    private String name;

    public Chapter(String title, String hrefUrl, String name) {
        this.title = title;
        this.hrefUrl = hrefUrl;
        this.name = name;
    }

    protected Chapter(Parcel in) {
        title = in.readString();
        hrefUrl = in.readString();
        name = in.readString();
    }

    public static final Creator<Chapter> CREATOR = new Creator<Chapter>() {
        @Override
        public Chapter createFromParcel(Parcel in) {
            return new Chapter(in);
        }

        @Override
        public Chapter[] newArray(int size) {
            return new Chapter[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getHrefUrl() {
        return hrefUrl;
    }

    public void setHrefUrl(String hrefUrl) {
        this.hrefUrl = hrefUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(hrefUrl);
        parcel.writeString(name);
    }

    @Override
    public String toString() {
        return "Chapter{" +
                "title='" + title + '\'' +
                ", hrefUrl='" + hrefUrl + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
