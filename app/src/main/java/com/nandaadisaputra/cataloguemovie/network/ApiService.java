package com.nandaadisaputra.cataloguemovie.network;

import com.nandaadisaputra.cataloguemovie.BuildConfig;
import com.nandaadisaputra.cataloguemovie.model.movie.ResponseMovie;
import com.nandaadisaputra.cataloguemovie.model.tv.ResponseTv;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    @GET("discover/movie?api_key=" + BuildConfig.API_KEY + "&language=en-US")
    Call<ResponseMovie> getDataMovie();

    @GET("discover/tv?api_key=" + BuildConfig.API_KEY + "&language=en-US")
    Call<ResponseTv> getTvData();

    @GET("search/movie?api_key=" + BuildConfig.API_KEY + "&language=en-US")
    Call<ResponseMovie> searchMovie(@Query("query") String movie);

    @GET("search/tv?api_key=" + BuildConfig.API_KEY + "&language=en-US")
    Call<ResponseTv> searchTv(@Query("query") String tvshow);

    @GET("discover/movie?api_key=" + BuildConfig.API_KEY)
    Call<ResponseMovie> getReleaseToday(@Query("primary_release_date.gte") String tgl1,
                                        @Query("primary_release_date.lte") String tgl2);


}
