package com.instagram.downloader.ViewModel.Api.Data;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ResponseModel implements Serializable {
    @SerializedName("graphql")
    private GraphModel graphql;

    public GraphModel getGraphql() {
        return this.graphql;
    }

    public void setGraphql(GraphModel graphql2) {
        this.graphql = graphql2;
    }

}
