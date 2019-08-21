package com.nandaadisaputra.cataloguemovie.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.nandaadisaputra.cataloguemovie.BuildConfig;
import com.nandaadisaputra.cataloguemovie.R;
import com.nandaadisaputra.cataloguemovie.activity.detail.DetailTvActivity;
import com.nandaadisaputra.cataloguemovie.model.tv.ResultsTv;

import java.util.List;

public class TvAdapter extends RecyclerView.Adapter<TvAdapter.ViewHolder> {

    private Context context;
    private List<ResultsTv> listitem;

    public TvAdapter(Context context, List<ResultsTv> resultsItemMovie) {
        this.context = context;
        this.listitem = resultsItemMovie;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemRow = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_movie, viewGroup, false);
        return new ViewHolder(itemRow);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder,  int i) {

        viewHolder.tvJudul.setText(listitem.get(i).getName());
        viewHolder.tvDateRelease.setText(listitem.get(i).getFirstAirDate());
        viewHolder.tvOverview.setText(listitem.get(i).getOverview());
        viewHolder.tvOriginalLanguage.setText(listitem.get(i).getOriginalLanguage());
        viewHolder.tvPopularity.setText(String.valueOf(listitem.get(i).getPopularity()));
        viewHolder.tvVote.setText(String.valueOf(listitem.get(i).getVoteAverage()));
        Glide.with(context).load(BuildConfig.BASE_URL_IMAGE + listitem.get(i).getPosterPath()).into(viewHolder.ivGambar);

        viewHolder.btnDetail.setOnClickListener(v -> {
            Intent btn_detailtv = new Intent(context, DetailTvActivity.class);
            btn_detailtv.putExtra(DetailTvActivity.EXTRATV, listitem.get(i));
            context.startActivity(btn_detailtv);
        });

        viewHolder.btnShare.setOnClickListener(v -> {
            Intent sharingIntent = new Intent(Intent.ACTION_VIEW);
            sharingIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            sharingIntent.setData(Uri.parse("https://web.whatsapp.com/"));
            context.startActivity(sharingIntent);
        });
    }
    @Override
    public int getItemCount() {
        return listitem.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvJudul, tvDateRelease, tvVote, tvOverview, tvOriginalLanguage, tvPopularity;
        private ImageView ivGambar;
        private Button btnShare, btnDetail;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvJudul = itemView.findViewById(R.id.tv_judulMovie);
            tvDateRelease = itemView.findViewById(R.id.tv_datemovie);
            tvVote = itemView.findViewById(R.id.tv_votemovie);
            ivGambar= itemView.findViewById(R.id.iv_gambarmovie);
            tvOverview = itemView.findViewById(R.id.tv_overviewmovie);
            tvOriginalLanguage = itemView.findViewById(R.id.tv_originallanguagemovie);
            btnDetail = itemView.findViewById(R.id.btn_detailmovie);
            btnShare = itemView.findViewById(R.id.btn_sharemovie);
            tvPopularity = itemView.findViewById(R.id.tv_popularitymovie);
        }
    }
}
