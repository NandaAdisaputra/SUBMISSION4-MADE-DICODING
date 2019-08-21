package com.nandaadisaputra.cataloguemovie.activity.detail;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import com.github.ivbaranov.mfb.MaterialFavoriteButton;
import com.nandaadisaputra.cataloguemovie.BuildConfig;
import com.nandaadisaputra.cataloguemovie.R;
import com.nandaadisaputra.cataloguemovie.roomdatabase.movie.MovieDatabase;
import com.nandaadisaputra.cataloguemovie.model.movie.ResultsMovie;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailMovieActivity extends AppCompatActivity {

    public static final String EXTRAMOVIE = "extra_movie";


    @BindView(R.id.iv_imagemovie)
    ImageView ivImageMovie;
    @BindView(R.id.iv_backdrop)
    ImageView ivBackdrop;
    @BindView(R.id.tv_judulMovie)
    TextView tvJudulMovie;
    @BindView(R.id.tv_releasedate)
    TextView tvReleaseDate;
    @BindView(R.id.tvDetailScore)
    TextView tvDetailScore;
    @BindView(R.id.tv_descriptionmovie)
    TextView tvDescriptionmovie;
    @BindView(R.id.progressBarDetailMovie)
    ProgressBar progressBarDetailMovie;
    @BindView(R.id.bintang)
    ImageView bintang;
    @BindView(R.id.descripsimovie)
    TextView descripsiMovie;
    ResultsMovie resultsMovie;
    @BindView(R.id.judulMovie)
    TextView JudulMovie;
    private MovieDatabase movieDatabase;

    MaterialFavoriteButton materialFavoriteButtonNice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);
        ButterKnife.bind(this);

        materialFavoriteButtonNice = findViewById(R.id.favorite_nice);
        resultsMovie = getIntent().getParcelableExtra(EXTRAMOVIE);

        if (savedInstanceState != null) {
            showLoading(false);
        } else {
            showLoading(true);

        }


        movieDatabase = MovieDatabase.getMovieDatabase(this);
        if (movieDatabase.movieDao().selectItemNoCursor(String.valueOf(resultsMovie.getId())) != null) {
            materialFavoriteButtonNice.setFavorite(true);
        }
        bintang.setVisibility(View.GONE);
        descripsiMovie.setVisibility(View.GONE);
        materialFavoriteButtonNice.setVisibility(View.GONE);
        setUpDelay();

        initFavorite(this);


    }

    private void setUpDelay() {
            bintang.setVisibility(View.VISIBLE);
            descripsiMovie.setVisibility(View.VISIBLE);
            materialFavoriteButtonNice.setVisibility(View.VISIBLE);
            if (getSupportActionBar()!=null)
            getSupportActionBar().setTitle(getString(R.string.detailMovie) + " " + resultsMovie.getOriginalTitle());
            Glide.with(DetailMovieActivity.this).load(BuildConfig.BASE_URL_IMAGE + resultsMovie.getPosterPath()).into(ivImageMovie);
            Glide.with(DetailMovieActivity.this).load(BuildConfig.BASE_URL_IMAGE + resultsMovie.getBackdropPath()).into(ivBackdrop);
            tvJudulMovie.setText(resultsMovie.getOriginalTitle());
            tvReleaseDate.setText(resultsMovie.getReleaseDate());
            tvDetailScore.setText(String.valueOf(resultsMovie.getVoteAverage()));
            tvDescriptionmovie.setText(resultsMovie.getOverview());
            showLoading(false);
    }

    private void initFavorite(final Context context) {
        materialFavoriteButtonNice.setOnFavoriteChangeListener(
                (buttonView, favorite) -> {
                    if (favorite) {
                        movieDatabase = MovieDatabase.getMovieDatabase(context);
                        movieDatabase.movieDao().insert(resultsMovie);
                        Toast.makeText(DetailMovieActivity.this, getString( R.string.addFavorite ), Toast.LENGTH_SHORT).show();

                    } else {
                        movieDatabase = MovieDatabase.getMovieDatabase(context);
                        movieDatabase.movieDao().deleteById(resultsMovie.getId());
                        Toast.makeText(DetailMovieActivity.this, getString(R.string.deleteFavorite ), Toast.LENGTH_SHORT).show();
                    }

                });

    }



    private void showLoading(Boolean state) {
        if (state) {
            progressBarDetailMovie.setVisibility(View.VISIBLE);
        } else {
            progressBarDetailMovie.setVisibility(View.GONE);
        }
    }
}
