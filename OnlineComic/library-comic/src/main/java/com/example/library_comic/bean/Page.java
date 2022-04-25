package com.example.library_comic.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class Page implements Parcelable {

    public String title;
    public String imgUrl;
    public String imgLocalPath;
    public int index;

    public Page(String title, String imgUrl, int index) {
        this.title = title;
        this.imgUrl = imgUrl;
        this.index = index;
    }

    public Page(String title, String imgUrl, String imgLocalPath, int index) {
        this.title = title;
        this.imgUrl = imgUrl;
        this.imgLocalPath = imgLocalPath;
        this.index = index;
    }

    protected Page(Parcel in) {
        title = in.readString();
        imgUrl = in.readString();
        imgLocalPath = in.readString();
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(imgUrl);
        parcel.writeString(imgLocalPath);
        parcel.writeInt(index);
    }
}
