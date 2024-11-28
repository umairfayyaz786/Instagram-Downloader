package com.instagram.downloader.ViewModel.Api.Data;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class EdgeModel implements Serializable {
    @SerializedName("node")
    private NodeModel node;

    public NodeModel getNode() {
        return this.node;
    }

    public void setNode(NodeModel node2) {
        this.node = node2;
    }

}
