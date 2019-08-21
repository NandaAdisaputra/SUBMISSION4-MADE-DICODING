package com.nandaadisaputra.cataloguemovie.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nandaadisaputra.cataloguemovie.R;
import com.nandaadisaputra.cataloguemovie.adapter.favorit.FavoritMovieAdapter;
import com.nandaadisaputra.cataloguemovie.roomdatabase.movie.MovieDatabase;
import com.nandaadisaputra.cataloguemovie.model.movie.ResultsMovie;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class FavoriteMovie extends Fragment {


    @BindView(R.id.recyclerViewFavoriteMovie)
    RecyclerView recyclerViewFavoriteMovie;
    @BindView(R.id.progressBarFavoritMovie)
    ProgressBar progressBarFavoritMovie;
    private Unbinder unbinder;

    private MovieDatabase movieDatabase;
    private FavoritMovieAdapter movieAdapter;

    private List<ResultsMovie> resultsItemMovies = new ArrayList<>();


    private void setFragmentManager(FragmentManager fragmentManager) {
    }

    public static FavoriteMovie newInstance(FragmentManager fragmentManager) {
        FavoriteMovie fragment = new FavoriteMovie();
        fragment.setFragmentManager(fragmentManager);
        return fragment;
    }

    public FavoriteMovie() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_favorite_movie, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Loading(true);
        movieDatabase = MovieDatabase.getMovieDatabase(getActivity());
        getFavorite();
        setupRecyclerView();

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                removeFav((long) viewHolder.itemView.getTag());
                resultsItemMovies.remove(resultsItemMovies.get(viewHolder.getAdapterPosition()));
                movieAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());
            }
        }).attachToRecyclerView(recyclerViewFavoriteMovie);
    }

    private void removeFav(long tag) {
        movieDatabase = MovieDatabase.getMovieDatabase(getActivity());
        movieDatabase.movieDao().deleteById(tag);
        Toast.makeText(getActivity(), getString(R.string.successDelete), Toast.LENGTH_SHORT).show();
    }

    private void getFavorite() {
        if (movieDatabase.movieDao().getFavoriteMovie() == null) {
            Toast.makeText(getActivity(), getString(R.string.noFavoriteData), Toast.LENGTH_SHORT).show();
            Loading(false);
        } else {
            movieDatabase.movieDao().getFavoriteMovie();
            Loading(false);
        }
    }

    private void setupRecyclerView() {
        resultsItemMovies = movieDatabase.movieDao().getFavoriteNoCursor();
        if (movieAdapter == null) {
            movieAdapter = new FavoritMovieAdapter(getActivity(), resultsItemMovies);
            recyclerViewFavoriteMovie.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerViewFavoriteMovie.setAdapter(movieAdapter);
            recyclerViewFavoriteMovie.setItemAnimator(new DefaultItemAnimator());
            recyclerViewFavoriteMovie.setNestedScrollingEnabled(true);
        } else {
            movieAdapter.notifyDataSetChanged();
        }
    }

    private void Loading(Boolean state) {
        if (state) {
            progressBarFavoritMovie.setVisibility(View.VISIBLE);
        } else {
            progressBarFavoritMovie.setVisibility(View.GONE);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}
