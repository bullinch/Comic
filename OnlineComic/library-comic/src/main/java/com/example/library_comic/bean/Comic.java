package com.example.library_comic.bean;


import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class Comic implements Parcelable {
    @Id
    private Long id;
    private String title;
    private String author;
    private String comicUrl;
    private String coverUrl;
    private String introduce;
    private String sourceName;
    private String sourceUrl;
    private Long favoriteStamp;  // 时间戳
    private Long historyStamp;  // 时间戳
    private String readChapter;  // 阅读的章节
    private String readChapterUrl;  // 阅读的章节Url
    private Integer readPage;  // 阅读的页

    public Comic(String title, String comicUrl, String coverUrl) {
        this.title = title;
        this.comicUrl = comicUrl;
        this.coverUrl = coverUrl;
    }

    @Generated(hash = 344936144)
    public Comic(Long id, String title, String author, String comicUrl,
            String coverUrl, String introduce, String sourceName, String sourceUrl,
            Long favoriteStamp, Long historyStamp, String readChapter,
            String readChapterUrl, Integer readPage) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.comicUrl = comicUrl;
        this.coverUrl = coverUrl;
        this.introduce = introduce;
        this.sourceName = sourceName;
        this.sourceUrl = sourceUrl;
        this.favoriteStamp = favoriteStamp;
        this.historyStamp = historyStamp;
        this.readChapter = readChapter;
        this.readChapterUrl = readChapterUrl;
        this.readPage = readPage;
    }

    @Generated(hash = 1347984162)
    public Comic() {
    }


    protected Comic(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readLong();
        }
        title = in.readString();
        author = in.readString();
        comicUrl = in.readString();
        coverUrl = in.readString();
        introduce = in.readString();
        sourceName = in.readString();
        sourceUrl = in.readString();
        if (in.readByte() == 0) {
            favoriteStamp = null;
        } else {
            favoriteStamp = in.readLong();
        }
        if (in.readByte() == 0) {
            historyStamp = null;
        } else {
            historyStamp = in.readLong();
        }
        readChapter = in.readString();
        readChapterUrl = in.readString();
        if (in.readByte() == 0) {
            readPage = null;
        } else {
            readPage = in.readInt();
        }
    }

    public static final Creator<Comic> CREATOR = new Creator<Comic>() {
        @Override
        public Comic createFromParcel(Parcel in) {
            return new Comic(in);
        }

        @Override
        public Comic[] newArray(int size) {
            return new Comic[size];
        }
    };

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return this.author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getComicUrl() {
        return this.comicUrl;
    }

    public void setComicUrl(String comicUrl) {
        this.comicUrl = comicUrl;
    }

    public String getCoverUrl() {
        return this.coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public String getIntroduce() {
        return this.introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public String getSourceName() {
        return this.sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public String getSourceUrl() {
        return this.sourceUrl;
    }

    public void setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }

    public Long getFavoriteStamp() {
        return this.favoriteStamp;
    }

    public void setFavoriteStamp(Long favoriteStamp) {
        this.favoriteStamp = favoriteStamp;
    }

    public Long getHistoryStamp() {
        return this.historyStamp;
    }

    public void setHistoryStamp(Long historyStamp) {
        this.historyStamp = historyStamp;
    }

    public String getReadChapterUrl() {
        return this.readChapterUrl;
    }

    public void setReadChapterUrl(String readChapterUrl) {
        this.readChapterUrl = readChapterUrl;
    }

    public Integer getReadPage() {
        return this.readPage;
    }

    public void setReadPage(Integer readPage) {
        this.readPage = readPage;
    }

    @Override
    public String toString() {
        return "Comic{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", comicUrl='" + comicUrl + '\'' +
                ", coverUrl='" + coverUrl + '\'' +
                ", introduce='" + introduce + '\'' +
                ", sourceName='" + sourceName + '\'' +
                ", sourceUrl='" + sourceUrl + '\'' +
                ", favoriteStamp=" + favoriteStamp +
                ", historyStamp=" + historyStamp +
                ", readChapter='" + readChapter + '\'' +
                ", readChapterUrl='" + readChapterUrl + '\'' +
                ", readPage=" + readPage +
                '}';
    }

    public String getReadChapter() {
        return this.readChapter;
    }

    public void setReadChapter(String readChapter) {
        this.readChapter = readChapter;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        if (id == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeLong(id);
        }
        parcel.writeString(title);
        parcel.writeString(author);
        parcel.writeString(comicUrl);
        parcel.writeString(coverUrl);
        parcel.writeString(introduce);
        parcel.writeString(sourceName);
        parcel.writeString(sourceUrl);
        if (favoriteStamp == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeLong(favoriteStamp);
        }
        if (historyStamp == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeLong(historyStamp);
        }
        parcel.writeString(readChapter);
        parcel.writeString(readChapterUrl);
        if (readPage == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(readPage);
        }
    }
}
