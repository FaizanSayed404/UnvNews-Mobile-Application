package com.unvnews.unvnews;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<ViewHolder> {
    Context context;
    List<Articles> articles;

    public SearchAdapter(Context context, List<Articles> articles) {
        this.context = context;
        this.articles = articles;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context)
                .inflate(R.layout.listitems,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {

        final String NewsUrl = articles.get(position).getUrl();

        holder.textView.setText(articles.get(position).getTitle());
        Glide.with(context)
                .load(articles.get(position).getUrlToImage())
                .into(holder.imageView);
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("URL", NewsUrl);
                intent.setClass(context, BrowseWeb.class);
                context.startActivity(intent);
            }
        });

        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("URL", NewsUrl);
                intent.setClass(context, BrowseWeb.class);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }
}

class ViewHolder extends RecyclerView.ViewHolder{
    TextView textView;
    ImageView imageView;
    public ViewHolder(@NonNull @NotNull View itemView) {
        super(itemView);
        textView = itemView.findViewById(R.id.textViewTitle);
        imageView = itemView.findViewById(R.id.imageViewThumbnail);
    }
}
