package com.example.library_comic.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class Chapter implements Parcelable {

    public String comicName;
    public String comicUrl;
    public String title;
    public String hrefUrl;
    public String name;
    public int downloadState;

    public Chapter(String title, String hrefUrl, String name) {
        this.title = title;
        this.hrefUrl = hrefUrl;
        this.name = name;
    }

    public Chapter(String comicName, String comicUrl, String title, String hrefUrl, String name) {
        this.comicName = comicName;
        this.comicUrl = comicUrl;
        this.title = title;
        this.hrefUrl = hrefUrl;
        this.name = name;
    }

    protected Chapter(Chapter chapter) {
        comicName = chapter.comicName;
        title = chapter.title;
        hrefUrl = chapter.hrefUrl;
        name = chapter.name;
    }

    public Chapter() {
    }

    protected Chapter(Parcel in) {
        comicName = in.readString();
        comicUrl = in.readString();
        title = in.readString();
        hrefUrl = in.readString();
        name = in.readString();
        downloadState = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(comicName);
        dest.writeString(comicUrl);
        dest.writeString(title);
        dest.writeString(hrefUrl);
        dest.writeString(name);
        dest.writeInt(downloadState);
    }

    @Override
    public int describeContents() {
        return 0;
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

    public String getComicName() {
        return comicName;
    }

    public void setComicName(String comicName) {
        this.comicName = comicName;
    }

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

    public int getDownloadState() {
        return downloadState;
    }

    public void setDownloadState(int downloadState) {
        this.downloadState = downloadState;
    }

    @Override
    public String toString() {
        return "Chapter{" +
                "comicName='" + comicName + '\'' +
                ", comicUrl='" + comicUrl + '\'' +
                ", title='" + title + '\'' +
                ", hrefUrl='" + hrefUrl + '\'' +
                ", name='" + name + '\'' +
                ", downloadState=" + downloadState +
                '}';
    }
}
