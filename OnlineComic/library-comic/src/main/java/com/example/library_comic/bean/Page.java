package com.example.library_comic.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class Page implements Parcelable {

    private String title;
    private String imgUrl;
    private int index;

    public Page(String title, String imgUrl, int index) {
        this.title = title;
        this.imgUrl = imgUrl;
        this.index = index;
    }

    protected Page(Parcel in) {
        title = in.readString();
        imgUrl = in.readString();
        index = in.readInt();
    }

    public static final Creator<Page> CREATOR = new Creator<Page>() {
        @Override
        public Page createFromParcel(Parcel in) {
            return new Page(in);
        }

        @Override
        public Page[] newArray(int size) {
            return new Page[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(imgUrl);
        parcel.writeInt(index);
    }

    @Override
    public String toString() {
        return "Page{" +
                "title='" + title + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                ", index=" + index +
                '}';
    }
}
