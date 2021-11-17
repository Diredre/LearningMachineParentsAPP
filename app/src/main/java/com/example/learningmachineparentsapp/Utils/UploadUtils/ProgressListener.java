package com.example.learningmachineparentsapp.Utils.UploadUtils;

public interface ProgressListener {
    void onProgress(long currentBytes, long contentLength, boolean done);
}