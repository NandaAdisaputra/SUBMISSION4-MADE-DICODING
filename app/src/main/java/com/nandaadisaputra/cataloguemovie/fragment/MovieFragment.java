package com.nandaadisaputra.cataloguemovie.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nandaadisaputra.cataloguemovie.R;
import com.nandaadisaputra.cataloguemovie.activity.search.SearchMovieActivity;
import com.nandaadisaputra.cataloguemovie.adapter.MovieAdapter;
import com.nandaadisaputra.cataloguemovie.model.movie.ResultsMovie;
import com.nandaadisaputra.cataloguemovie.viewmodel.MainViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MovieFragment extends Fragment {

    @BindView(R.id.searchMovie)
    SearchView searchMovie;
    @BindView(R.id.progressBarMovie)
    ProgressBar progressBarMovie;
    @BindView(R.id.recyclerMovie)
    RecyclerView recyclerViewMovie;
    Unbinder unbinder;

    private MovieAdapter movieAdapter;

    List<ResultsMovie> resultsMovieArrayList = new ArrayList<>();
    MainViewModel movieViewModel;

    public static final String MOVIE = "extra_movie";


    public MovieFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_movie, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        searchMovie.setOnClickListener(v -> startActivity(new Intent(getActivity(), SearchMovieActivity.class)));
        Loading(true);

        getMovie();

        setupRecyclerView();

    }


    public void getMovie() {
        movieViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        movieViewModel.initMovie();
        movieViewModel.getMovieModel().observe(this, responseMovie -> {
            if ((responseMovie != null ? responseMovie.getResults() : null) == null) {
                Toast.makeText(getActivity(), getString(R.string.nodatafound), Toast.LENGTH_SHORT).show();
                Loading(false);
            } else {
                List<ResultsMovie> resultsMovie = responseMovie.getResults();
                resultsMovieArrayList.addAll(resultsMovie);
                movieAdapter.notifyDataSetChanged();
                Loading(false);

            }
        });

    }

    private void setupRecyclerView() {
        if (movieAdapter == null) {
            movieAdapter = new MovieAdapter(getActivity(), resultsMovieArrayList);
            recyclerViewMovie.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerViewMovie.setAdapter(movieAdapter);
            recyclerViewMovie.setItemAnimator(new DefaultItemAnimator());
            recyclerViewMovie.setNestedScrollingEnabled(true);
        } else {
            movieAdapter.notifyDataSetChanged();
        }
    }

    private void Loading(Boolean state) {
        if (state) {
            progressBarMovie.setVisibility(View.VISIBLE);
        } else {
            progressBarMovie.setVisibility(View.GONE);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getMovie();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


}
