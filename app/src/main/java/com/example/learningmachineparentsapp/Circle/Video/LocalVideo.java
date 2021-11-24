package com.example.learningmachineparentsapp.Circle.Video;

import android.net.Uri;

import androidx.annotation.NonNull;

import org.jetbrains.annotations.NotNull;

public class LocalVideo {
    private String path;
    private long id;
    private Uri uri;
    private long duration;
    private String name;
    private long size;
    private long date;

    public LocalVideo(String path, long id, Uri uri, long duration, String name, long size, long date) {
        this.path = path;
        this.id = id;
        this.uri = uri;
        this.duration = duration;
        this.name = name;
        this.size = size;
        this.date = date;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    @NonNull
    @NotNull
    @Override
    public String toString() {
        return "path："+path+"\nid："+"id"+id+"\nuri："+uri+"\nduration："+duration+
                "\nname："+name+"\nsize："+size+"\ndate："+date;
    }
}
