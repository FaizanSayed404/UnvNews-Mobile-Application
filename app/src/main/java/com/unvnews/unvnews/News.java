package com.unvnews.unvnews;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.annotations.SerializedName;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class News {

    @SerializedName("articles")
    private List<Articles> articles;

    // This helps debug
    @SerializedName("status")
    private String status;

    public List<Articles> getArticles() {
        return articles;
    }

}
