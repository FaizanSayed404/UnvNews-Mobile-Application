package com.unvnews.unvnews;

import android.app.Application;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = Articles.class , version = 1, exportSchema = false)
public abstract class ArticlesDatabase extends RoomDatabase {

    private static ArticlesDatabase Instance;

    public static synchronized ArticlesDatabase getInstance(Application application) {
        if (Instance == null) {
            Instance = Room.databaseBuilder(application.getApplicationContext(),
                    ArticlesDatabase.class, "notes_table")
                    .allowMainThreadQueries()
                    .build();
        }
        return Instance;
    }

    public abstract ArticlesDao articlesDao();
}
