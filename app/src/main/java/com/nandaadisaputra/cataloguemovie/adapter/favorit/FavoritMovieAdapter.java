package com.nandaadisaputra.cataloguemovie.adapter.favorit;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.nandaadisaputra.cataloguemovie.BuildConfig;
import com.nandaadisaputra.cataloguemovie.R;
import com.nandaadisaputra.cataloguemovie.activity.detail.DetailMovieActivity;
import com.nandaadisaputra.cataloguemovie.roomdatabase.movie.MovieDatabase;
import com.nandaadisaputra.cataloguemovie.model.movie.ResultsMovie;

import java.util.List;

public class FavoritMovieAdapter extends RecyclerView.Adapter<FavoritMovieAdapter.ViewHolder> {

    private Context context;
    private List<ResultsMovie>  listitem;

    public FavoritMovieAdapter(Context context, List<ResultsMovie> resultsMovie) {
        this.context = context;
        this. listitem = resultsMovie;
    }

    @NonNull
    @Override
    public FavoritMovieAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_favorit, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final FavoritMovieAdapter.ViewHolder viewHolder, int i) {
        viewHolder.tvTitle.setText(listitem.get(i).getOriginalTitle());
        viewHolder.tvDateRelease.setText(listitem.get(i).getReleaseDate());
        viewHolder.tvOverview.setText(listitem.get(i).getOverview());
        viewHolder.tvOriginalLanguage.setText(listitem.get(i).getOriginalLanguage());
        viewHolder.tvPopularity.setText(String.valueOf(listitem.get(i).getPopularity()));
        viewHolder.tvVote.setText(String.valueOf(listitem.get(i).getVoteAverage()));
        Glide.with(context).load(BuildConfig.BASE_URL_IMAGE + listitem.get(i).getPosterPath()).into(viewHolder.ivPoster);
        long id = listitem.get(i).getId();

        viewHolder.itemView.setTag(id);
        viewHolder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetailMovieActivity.class);
            intent.putExtra(DetailMovieActivity.EXTRAMOVIE, listitem.get(viewHolder.getAdapterPosition()));
            context.startActivity(intent);
        });
        viewHolder.itemView.setOnLongClickListener(v -> {
            AlertDialog.Builder dialog;
            dialog = new AlertDialog.Builder(context);
            dialog.setCancelable(true);
            dialog.setIcon(R.drawable.ic_delete_black_24dp);
            dialog.setTitle(R.string.KonfirmasiFavorite);
            dialog.setPositiveButton(R.string.ya, (dialog12, which) -> {
                dialog12.dismiss();
                MovieDatabase movieDatabase = MovieDatabase.getMovieDatabase(context);
                movieDatabase.movieDao().deleteById(listitem.get(viewHolder.getAdapterPosition()).getId());
                Toast.makeText(context,R.string.successDelete, Toast.LENGTH_SHORT).show();
                listitem.remove(listitem.get(viewHolder.getAdapterPosition()));
                notifyItemRemoved(viewHolder.getAdapterPosition());
            });
            dialog.setNegativeButton(R.string.tidak, (dialog1, which) -> dialog1.dismiss());
            dialog.show();
            return true;
        });
    }


    @Override
    public int getItemCount() {
        return listitem.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvTitle, tvDateRelease, tvVote, tvOverview, tvOriginalLanguage, tvGenre, tvPopularity;
        private ImageView ivPoster;
        private Button btnShare, btnDetail;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.tv_titlefavorit);
            tvDateRelease = itemView.findViewById(R.id.tv_datefavorit);
            tvVote = itemView.findViewById(R.id.tv_votefavorit);
            ivPoster = itemView.findViewById(R.id.iv_imgfavorit);
            tvOverview = itemView.findViewById(R.id.tv_overviewfavorit);
            tvOriginalLanguage = itemView.findViewById(R.id.tv_originallanguagefavorit);
            btnDetail = itemView.findViewById(R.id.btn_detailfavorit);
            btnShare = itemView.findViewById(R.id.btn_sharefavorit);
            tvPopularity = itemView.findViewById(R.id.tv_popularityfavorit);
        }
    }
}