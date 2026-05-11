package com.unvnews.unvnews;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.Holder> {
    Context context;
    List<Articles> list;
    OnReadLaterClickedListener onReadLaterClickedListener;
    String activityName;
    OnRemoveButtonClickedListener onRemoveButtonClickedListener;

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public void setOnRemoveButtonClickedListener(OnRemoveButtonClickedListener onRemoveButtonClickedListener) {
        this.onRemoveButtonClickedListener = onRemoveButtonClickedListener;
    }

    public void setOnReadLaterClickedListener(OnReadLaterClickedListener onReadLaterClickedListener) {
        this.onReadLaterClickedListener = onReadLaterClickedListener;
    }

    public void setList(List<Articles> list) {
        this.list = list;
    }


    @NonNull
    @Override
    public MyAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context)
                .inflate(R.layout.list_items, parent, false);
        return new Holder(view);
    }

    @SuppressLint({"SetTextI18n", "UseCompatLoadingForDrawables"})
    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.title.setText(list.get(position).getTitle());
        Glide.with(context)
                .load(list.get(position).getUrlToImage())
                .placeholder(R.drawable.news_thumbnail)
                .into(holder.img);
        final String NewsUrl = list.get(position).getUrl();
        holder.itemView.animate().scaleX(1).scaleY(1).setDuration(400).start();
        holder.publishedAt.setText(list.get(position).getPublishedAt().replace("T", " - ").replace("Z", ""));
        holder.title.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.putExtra("URL", NewsUrl);
            intent.setClass(context, BrowseWeb.class);
            context.startActivity(intent);
        });
        holder.img.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.putExtra("URL", NewsUrl);
            intent.setClass(context, BrowseWeb.class);
            context.startActivity(intent);
        });

        holder.imageViewShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, list.get(position).getUrl());
                sendIntent.setType("text/plain");
                Intent shareIntent = Intent.createChooser(sendIntent, "Choose App To Share");
                context.startActivity(shareIntent);
            }
        });

        if (activityName.equals("FavouritesActivity")) {
            holder.imageViewReadLater.setImageDrawable(context.getDrawable(R.drawable.ic_baseline_delete_24));
        }

        holder.imageViewReadLater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (activityName.equals("FavouritesActivity")) {
                    onRemoveButtonClickedListener.onRemoveButtonClicked(list.get(position).getTitle(),position);
                } else {
                    onReadLaterClickedListener.onReadLaterClicked(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class Holder extends RecyclerView.ViewHolder {
        TextView title, publishedAt;
        ImageView img;
        CardView cardView;
        ImageView imageViewReadLater,imageViewShare;

        public Holder(@NonNull View itemView) {
            super(itemView);
            itemView.setScaleY(0);
            itemView.setScaleX(0);
            title = itemView.findViewById(R.id.textViewTitle);
            img = itemView.findViewById(R.id.imageViewThumbnail);
            cardView = itemView.findViewById(R.id.MaincardView);
            publishedAt = itemView.findViewById(R.id.textViewPublishedDate);
            imageViewReadLater = itemView.findViewById(R.id.imageButton);
            imageViewShare = itemView.findViewById(R.id.imageViewShare);
        }
    }
}
