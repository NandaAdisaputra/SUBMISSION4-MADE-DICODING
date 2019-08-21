package com.nandaadisaputra.cataloguemovie.repository;

import androidx.lifecycle.MutableLiveData;

import com.nandaadisaputra.cataloguemovie.model.tv.ResponseTv;
import com.nandaadisaputra.cataloguemovie.network.config.Client;
import  com.nandaadisaputra.cataloguemovie.network.ApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TvRepository {

    private static TvRepository tvRepository;

    public static TvRepository getInstance() {
        if (tvRepository == null) {
            tvRepository = new TvRepository();
        }
        return tvRepository;
    }

    private ApiService apiService;

    public TvRepository() {
        apiService = Client.getInitRetrofit();
    }

    public MutableLiveData<ResponseTv> getTV() {
        final MutableLiveData<ResponseTv> tvData = new MutableLiveData<>();
        apiService.getTvData().enqueue(new Callback<ResponseTv>() {
            @Override
            public void onResponse(Call<ResponseTv> call, Response<ResponseTv> response) {
                if ((response.body() != null ? response.body().getPage() : 0) > 0) {
                    tvData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<ResponseTv> call, Throwable t) {
                tvData.setValue(null);
            }
        });
        return tvData;
    }



}
