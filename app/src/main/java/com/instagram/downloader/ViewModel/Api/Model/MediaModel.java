package com.instagram.downloader.ViewModel.Api.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class MediaModel implements Serializable {
    @SerializedName("media")
    private MediaDataModel mediaDataModel;

    public MediaDataModel getMediaDataModel() {
        return this.mediaDataModel;
    }

    public void setMediaDataModel(MediaDataModel mediaDataModel2) {
        this.mediaDataModel = mediaDataModel2;
    }
}