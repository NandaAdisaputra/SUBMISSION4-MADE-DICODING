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
import com.nandaadisaputra.cataloguemovie.adapter.favorit.FavoritTvAdapater;
import com.nandaadisaputra.cataloguemovie.roomdatabase.tv.TvDatabase;
import com.nandaadisaputra.cataloguemovie.model.tv.ResultsTv;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class FavoriteTv extends Fragment {

    @BindView(R.id.recyclerViewFavoritTv)
    RecyclerView recyclerViewFavoritTv;
    @BindView(R.id.progressBarFavoritTv)
    ProgressBar progressBarFavoritTv;
    Unbinder unbinder;

    List<ResultsTv> resultsItemMovieArrayList = new ArrayList<>();
    TvDatabase tvDatabase;
    FavoritTvAdapater adapter;

    public void setFragmentManager(FragmentManager fragmentManager) {
    }

    public static FavoriteTv newInstance(FragmentManager fragmentManager) {
        FavoriteTv fragment = new FavoriteTv();
        fragment.setFragmentManager(fragmentManager);
        return fragment;
    }

    public FavoriteTv() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite_tv, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Loading(true);
        tvDatabase = TvDatabase.getTvDatabase(getActivity());
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
                resultsItemMovieArrayList.remove(resultsItemMovieArrayList.get(viewHolder.getAdapterPosition()));
                adapter.notifyItemRemoved(viewHolder.getAdapterPosition());

            }
        }).attachToRecyclerView(recyclerViewFavoritTv);
    }

    private void removeFav(long tag) {
        tvDatabase = TvDatabase.getTvDatabase(getActivity());
        tvDatabase.tvDao().deleteTv(tag);
        Toast.makeText(getActivity(), getString(R.string.successDelete), Toast.LENGTH_SHORT).show();
    }

    public void getFavorite() {
        if (tvDatabase.tvDao().getFavoriteTv() == null) {
            Toast.makeText(getActivity(), R.string.noFavoriteData, Toast.LENGTH_SHORT).show();
            Loading(false);
        } else {
            tvDatabase.tvDao().getFavoriteTv();
            Loading(false);
        }
    }

    private void setupRecyclerView() {
        resultsItemMovieArrayList = tvDatabase.tvDao().getFavoriteTv();
        if (adapter == null) {
            adapter = new FavoritTvAdapater(getActivity(), resultsItemMovieArrayList);
            recyclerViewFavoritTv.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerViewFavoritTv.setAdapter(adapter);
            recyclerViewFavoritTv.setItemAnimator(new DefaultItemAnimator());
            recyclerViewFavoritTv.setNestedScrollingEnabled(true);
        } else {
            adapter.notifyDataSetChanged();
        }
    }

    private void Loading(Boolean state) {
        if (state) {
            progressBarFavoritTv.setVisibility(View.VISIBLE);
        } else {
            progressBarFavoritTv.setVisibility(View.GONE);
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
