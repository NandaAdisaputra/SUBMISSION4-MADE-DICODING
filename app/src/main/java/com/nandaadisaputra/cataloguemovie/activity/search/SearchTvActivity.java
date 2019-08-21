package com.nandaadisaputra.cataloguemovie.activity.search;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nandaadisaputra.cataloguemovie.R;
import com.nandaadisaputra.cataloguemovie.adapter.TvAdapter;
import com.nandaadisaputra.cataloguemovie.model.tv.ResponseTv;
import com.nandaadisaputra.cataloguemovie.model.tv.ResultsTv;
import com.nandaadisaputra.cataloguemovie.network.config.Client;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchTvActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    @BindView(R.id.searchMovie)
    SearchView searchMovie;

    @BindView(R.id.recyclerViewMovie)
    RecyclerView recyclerViewMovie;
    @BindView(R.id.progressBarMovie)
    ProgressBar progressBarMovie;


    private TvAdapter tvAdapter;
    List<ResultsTv> resultsTvArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_search_movie);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);


        searchMovie.setQueryHint(getString(R.string.search_tv));
        searchMovie.setOnQueryTextListener(this);
        searchMovie.setIconified(false);
        searchMovie.clearFocus();

        setupRecyclerView();
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        recyclerViewMovie.setVisibility(View.GONE);
        Loading(true);
        Client.getInitRetrofit().searchTv(newText).enqueue(new Callback<ResponseTv>() {
            @Override
            public void onResponse(Call<ResponseTv> call, Response<ResponseTv> response) {
                Loading(false);
                recyclerViewMovie.setVisibility(View.VISIBLE);
                if ((response.body() != null ? response.body().getResults() : null) != null){
                    ResponseTv responseTv = response.body();
                    resultsTvArrayList = responseTv.getResults();
                    tvAdapter = new TvAdapter(SearchTvActivity.this, resultsTvArrayList);
                    recyclerViewMovie.setAdapter(tvAdapter);

                }else {
                    Loading(false);
                }
            }

            @Override
            public void onFailure(Call<ResponseTv> call, Throwable t) {
                Loading(false);
            }
        });
        return true;
    }

    private void Loading(Boolean state) {
        if (state) {
            progressBarMovie.setVisibility(View.VISIBLE);
        } else {
            progressBarMovie.setVisibility(View.GONE);
        }
    }


    private void setupRecyclerView() {
        if (tvAdapter == null) {
            tvAdapter = new TvAdapter(this, resultsTvArrayList);
            recyclerViewMovie.setLayoutManager(new LinearLayoutManager(this));
            recyclerViewMovie.setAdapter(tvAdapter);
            recyclerViewMovie.setItemAnimator(new DefaultItemAnimator());
            recyclerViewMovie.setNestedScrollingEnabled(true);
        } else {
            tvAdapter.notifyDataSetChanged();
        }
    }
}
