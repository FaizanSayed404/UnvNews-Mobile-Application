package com.unvnews.unvnews;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import org.jetbrains.annotations.NotNull;

@Entity(tableName = "articles_table",indices = @Index(value = "title",unique = true))
public class Articles {

    @PrimaryKey(autoGenerate = true)
    int newsId;

    @SerializedName("title")
    @ColumnInfo(name = "title")
    String title;

    @SerializedName("url")
    @ColumnInfo(name = "url")
    String url;

    @SerializedName("urlToImage")
    @ColumnInfo(name = "url_to_image")
    String urlToImage;

    @SerializedName("publishedAt")
    @ColumnInfo(name = "published_at")
    String publishedAt;

    public Articles(String title, String url, String urlToImage, String publishedAt) {
        this.title = title;
        this.url = url;
        this.urlToImage = urlToImage;
        this.publishedAt = publishedAt;
    }

    public Articles() {
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    @Override
    public @NotNull String toString() {
        return "Articles{" +
                "newsId=" + newsId +
                ", title='" + title + '\'' +
                ", url='" + url + '\'' +
                ", urlToImage='" + urlToImage + '\'' +
                ", publishedAt='" + publishedAt + '\'' +
                '}';
    }
}
