package com.nandaadisaputra.cataloguemovie.model.tv;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseTv {

    @SerializedName("page")
    private int page;

    @SerializedName("total_pages")
    private int totalPages;

    @SerializedName("results")
    private List<ResultsTv> results;

    @SerializedName("total_results")
    private int totalResults;

    public int getPage() {
        return page;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public List<ResultsTv> getResults() {
        return results;
    }

    public int getTotalResults() {
        return totalResults;
    }
}