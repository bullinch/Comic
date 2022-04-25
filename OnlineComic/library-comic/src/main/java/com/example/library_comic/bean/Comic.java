package com.example.library_comic.bean;


import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.Unique;

@Entity
public class Comic implements Parcelable {
    @Id
    public Long id;
    public String title;
    public String author;
    @Index(unique = true)//设置唯一性
    public String comicUrl;
    public String coverUrl;
    public String introduce;
    public int sourceId;
    public String sourceName;
    public String sourceUrl;
    public Long favoriteStamp;  // 时间戳
    public Long historyStamp;  // 时间戳
    public Long downloadStamp;  // 时间戳
    public String readChapter;  // 阅读的章节
    public String readChapterUrl;  // 阅读的章节Url
    public Integer readPage;  // 阅读的页

    public Comic(String title, String comicUrl, String coverUrl) {
        this.title = title;
        this.comicUrl = comicUrl;
        this.coverUrl = coverUrl;
    }

    @Generated(hash = 1925757871)
    public Comic(Long id, String title, String author, String comicUrl,
            String coverUrl, String introduce, int sourceId, String sourceName,
            String sourceUrl, Long favoriteStamp, Long historyStamp,
            Long downloadStamp, String readChapter, String readChapterUrl,
            Integer readPage) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.comicUrl = comicUrl;
        this.coverUrl = coverUrl;
        this.introduce = introduce;
        this.sourceId = sourceId;
        this.sourceName = sourceName;
        this.sourceUrl = sourceUrl;
        this.favoriteStamp = favoriteStamp;
        this.historyStamp = historyStamp;
        this.downloadStamp = downloadStamp;
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
        sourceId = in.readInt();
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
        if (in.readByte() == 0) {
            downloadStamp = null;
        } else {
            downloadStamp = in.readLong();
        }
        readChapter = in.readString();
        readChapterUrl = in.readString();
        if (in.readByte() == 0) {
            readPage = null;
        } else {
            readPage = in.readInt();
        }
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(id);
        }
        dest.writeString(title);
        dest.writeString(author);
        dest.writeString(comicUrl);
        dest.writeString(coverUrl);
        dest.writeString(introduce);
        dest.writeInt(sourceId);
        dest.writeString(sourceName);
        dest.writeString(sourceUrl);
        if (favoriteStamp == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(favoriteStamp);
        }
        if (historyStamp == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(historyStamp);
        }
        if (downloadStamp == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(downloadStamp);
        }
        dest.writeString(readChapter);
        dest.writeString(readChapterUrl);
        if (readPage == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(readPage);
        }
    }

    @Override
    public int describeContents() {
        return 0;
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

    @Override
    public String toString() {
        return "Comic{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", comicUrl='" + comicUrl + '\'' +
                ", coverUrl='" + coverUrl + '\'' +
                ", introduce='" + introduce + '\'' +
                ", sourceId=" + sourceId +
                ", sourceName='" + sourceName + '\'' +
                ", sourceUrl='" + sourceUrl + '\'' +
                ", favoriteStamp=" + favoriteStamp +
                ", historyStamp=" + historyStamp +
                ", downloadStamp=" + downloadStamp +
                ", readChapter='" + readChapter + '\'' +
                ", readChapterUrl='" + readChapterUrl + '\'' +
                ", readPage=" + readPage +
                '}';
    }

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

    public Long getDownloadStamp() {
        return this.downloadStamp;
    }

    public void setDownloadStamp(Long downloadStamp) {
        this.downloadStamp = downloadStamp;
    }

    public String getReadChapter() {
        return this.readChapter;
    }

    public void setReadChapter(String readChapter) {
        this.readChapter = readChapter;
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

    public int getSourceId() {
        return this.sourceId;
    }

    public void setSourceId(int sourceId) {
        this.sourceId = sourceId;
    }
}
