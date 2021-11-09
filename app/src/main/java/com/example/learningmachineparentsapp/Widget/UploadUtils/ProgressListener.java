package com.example.learningmachineparentsapp.Widget.UploadUtils;

public interface ProgressListener {
    void onProgress(long currentBytes, long contentLength, boolean done);
}