package com.unvnews.unvnews;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SportsFragment extends Fragment {

    MyAdapter myAdapter;
    RecyclerView recyclerView;
    List<Articles> articles;
    ProgressBar progressBar;
    Retrofit retrofit;
    public SportsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_show_news,container,false);
        recyclerView = view.findViewById(R.id.recyclerview);
        progressBar = view.findViewById(R.id.progress_bar);
        articles = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        progressBar.setVisibility(View.VISIBLE);
        load_data();
        return view;
    }
    private void load_data() {
        Models models = new Models();
        retrofit = new Retrofit.Builder()
                .baseUrl(models.getBASE_URL())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface apiInterface = retrofit.create(ApiInterface.class);

        Call<News> call = apiInterface.getArticleByCategory(models.getCOUNTRY(),"sports",models.getAPI_KEY());

        call.enqueue(new Callback<News>() {
            @Override
            public void onResponse(Call<News> call, Response<News> response) {

                if (response.body() != null)
                {
                    progressBar.setVisibility(View.INVISIBLE);
                    articles = response.body().getArticles();
                    myAdapter = new MyAdapter(getContext(),articles);
                    recyclerView.setAdapter(myAdapter);
                    myAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onFailure(Call<News> call, Throwable t) {

            }
        });


    }

}