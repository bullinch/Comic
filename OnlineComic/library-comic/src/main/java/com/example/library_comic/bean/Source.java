package com.example.library_comic.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class Source implements Parcelable {

    public int id;
    public String name;
    public String hostUrl;

    public Source(int id, String name, String hostUrl) {
        this.id = id;
        this.name = name;
        this.hostUrl = hostUrl;
    }

    protected Source(Parcel in) {
        id = in.readInt();
        name = in.readString();
        hostUrl = in.readString();
    }

    public static final Creator<Source> CREATOR = new Creator<Source>() {
        @Override
        public Source createFromParcel(Parcel in) {
            return new Source(in);
        }

        @Override
        public Source[] newArray(int size) {
            return new Source[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeString(hostUrl);
    }

    @Override
    public String toString() {
        return "Source{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", hostUrl='" + hostUrl + '\'' +
                '}';
    }
}
