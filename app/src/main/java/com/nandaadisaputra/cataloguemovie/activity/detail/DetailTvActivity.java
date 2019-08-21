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
import com.nandaadisaputra.cataloguemovie.roomdatabase.tv.TvDatabase;
import com.nandaadisaputra.cataloguemovie.model.tv.ResultsTv;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailTvActivity extends AppCompatActivity {

    public static final String EXTRATV = "extra_tvshow";

    @BindView(R.id.iv_imagetv)
    ImageView ivImageTv;
    @BindView(R.id.tv_titletv)
    TextView tvTitleTv;
    @BindView(R.id.iv_backdrop)
    ImageView ivBackdrop;
    @BindView(R.id.tvDetailScoreTv)
    TextView tvDetailScoreTv;
    @BindView(R.id.tv_descriptiontv)
    TextView tvDescriptionTv;
    @BindView(R.id.progressBarDetailTv)
    ProgressBar progressBarDetailTv;
    @BindView(R.id.bintangtv)
    ImageView bintangTv;
    @BindView(R.id.tv_releasedate)
    TextView tvReleaseDate;
    @BindView(R.id.descriptiontv)
    TextView descriptionTv;

    ResultsTv resultsTv;
    @BindView(R.id.titleTv)
    TextView titleTv;
    private TvDatabase tvDatabase;

    MaterialFavoriteButton materialFavoriteButtonNice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_tv);
        ButterKnife.bind(this);


        materialFavoriteButtonNice = findViewById(R.id.favorite_nice);
        resultsTv = getIntent().getParcelableExtra(EXTRATV);

        if (savedInstanceState != null) {
            showLoading(false);
        } else {
            showLoading(true);

        }

        tvDatabase = TvDatabase.getTvDatabase(this);
        if (tvDatabase.tvDao().selectItem(String.valueOf(resultsTv.getId())) != null) {
            materialFavoriteButtonNice.setFavorite(true);
        }
        bintangTv.setVisibility(View.GONE);
        descriptionTv.setVisibility(View.GONE);
        materialFavoriteButtonNice.setVisibility(View.GONE);
        setUpDelay();

        initFavorite(this);
    }

    private void setUpDelay() {
            bintangTv.setVisibility(View.VISIBLE);
            descriptionTv.setVisibility(View.VISIBLE);
            materialFavoriteButtonNice.setVisibility(View.VISIBLE);
            if (getSupportActionBar()!=null)
                getSupportActionBar().setTitle(getString(R.string.detailtv) + " " + resultsTv.getName());
        Glide.with(DetailTvActivity.this).load(BuildConfig.BASE_URL_IMAGE + resultsTv.getBackdropPath()).into(ivBackdrop);
            Glide.with(DetailTvActivity.this).load(BuildConfig.BASE_URL_IMAGE + resultsTv.getPosterPath()).into(ivImageTv);
            tvTitleTv.setText(resultsTv.getName());
            tvReleaseDate.setText(resultsTv.getFirstAirDate());
            tvDetailScoreTv.setText(String.valueOf(resultsTv.getVoteAverage()));
            tvDescriptionTv.setText(resultsTv.getOverview());
            showLoading(false);

    }


    private void initFavorite(final Context context) {
        materialFavoriteButtonNice.setOnFavoriteChangeListener(
                (buttonView, favorite) -> {
                    if (favorite) {
                        tvDatabase = TvDatabase.getTvDatabase(context);
                        tvDatabase.tvDao().InsertTv(resultsTv);
                        Toast.makeText(DetailTvActivity.this, getString(R.string.addFavorite ), Toast.LENGTH_SHORT).show();
                    } else {
                        tvDatabase = TvDatabase.getTvDatabase(context);
                        tvDatabase.tvDao().deleteTv(resultsTv.getId());
                        Toast.makeText(DetailTvActivity.this, getString(R.string.deleteFavorite ), Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private void showLoading(Boolean state) {
        if (state) {
            progressBarDetailTv.setVisibility(View.VISIBLE);
        } else {
            progressBarDetailTv.setVisibility(View.GONE);
        }
    }


}
