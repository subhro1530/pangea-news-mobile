package com.example.pangea_news.network;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NewsApi {
    @GET("top-headlines")
    Call<NewsResponse> getTopHeadlines(
            @Query("category") String category,
            @Query("lang") String language,
            @Query("country") String country,
            @Query("max") int maxResults,
            @Query("apikey") String apiKey
    );
}

