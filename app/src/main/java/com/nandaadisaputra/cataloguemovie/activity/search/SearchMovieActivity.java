package com.nandaadisaputra.cataloguemovie.activity.search;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nandaadisaputra.cataloguemovie.R;
import com.nandaadisaputra.cataloguemovie.adapter.MovieAdapter;
import com.nandaadisaputra.cataloguemovie.model.movie.ResponseMovie;
import com.nandaadisaputra.cataloguemovie.model.movie.ResultsMovie;
import com.nandaadisaputra.cataloguemovie.network.config.Client;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchMovieActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    @BindView(R.id.searchMovie)
    SearchView searchMovie;
    @BindView(R.id.frameLayout)
    FrameLayout frameLayout;
    @BindView(R.id.recyclerViewMovie)
    RecyclerView recyclerViewMovie;
    @BindView(R.id.progressBarMovie)
    ProgressBar progressBarMovie;

    private MovieAdapter movieAdapter;

    List<ResultsMovie> resultsItemMovieArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_movie);
        ButterKnife.bind(this);

        searchMovie.setQueryHint(getString(R.string.search_movie));
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
        showLoading(true);

        Client.getInitRetrofit().searchMovie(newText).enqueue(new Callback<ResponseMovie>() {
            @Override
            public void onResponse(Call<ResponseMovie> call, Response<ResponseMovie> response) {
                showLoading(false);
                recyclerViewMovie.setVisibility(View.VISIBLE);
                if ((response.body() != null ? response.body().getResults() : null) != null) {
                    ResponseMovie responseMovie = response.body();
                    resultsItemMovieArrayList = responseMovie.getResults();
                    movieAdapter = new MovieAdapter(SearchMovieActivity.this, resultsItemMovieArrayList);
                    recyclerViewMovie.setAdapter(movieAdapter);
                } else {
                    showLoading(false);
                }
            }

            @Override
            public void onFailure(Call<ResponseMovie> call, Throwable t) {
                showLoading(false);
            }
        });
        return true;
    }


    private void showLoading(Boolean state) {
        if (state) {
            progressBarMovie.setVisibility(View.VISIBLE);
        } else {
            progressBarMovie.setVisibility(View.GONE);
        }
    }


    private void setupRecyclerView() {
        if (movieAdapter == null) {
            movieAdapter = new MovieAdapter(this, resultsItemMovieArrayList);
            recyclerViewMovie.setLayoutManager(new LinearLayoutManager(this));
            recyclerViewMovie.setAdapter(movieAdapter);
            recyclerViewMovie.setItemAnimator(new DefaultItemAnimator());
            recyclerViewMovie.setNestedScrollingEnabled(true);
        } else {
            movieAdapter.notifyDataSetChanged();
        }
    }
}
