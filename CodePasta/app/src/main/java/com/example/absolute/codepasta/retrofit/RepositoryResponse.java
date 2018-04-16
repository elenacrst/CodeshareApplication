package com.example.absolute.codepasta.retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Absolute on 12/6/2017.
 */

public class RepositoryResponse {
    @SerializedName("items")
    @Expose
    private List<RepositoryData> items = new ArrayList<>();

    public void setItems(List<RepositoryData> items) {
        this.items = items;
    }

    public List<RepositoryData> getItems() {
        return items;
    }
}

// https://api.github.com
// /search/repositories?q=android&sort=updated&order=desc
// &per_page=20&page=1
