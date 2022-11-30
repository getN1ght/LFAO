package com.example.lfao.data;

import android.content.Context;
import androidx.room.*;


@Database(entities = {LoginTable.class}, version = 1, exportSchema = false)
public abstract class LoginDataBase extends RoomDatabase {

    public abstract LoginDao loginDoa();

    private static volatile LoginDataBase INSTANCE;

    public static LoginDataBase getDatabase(final Context context) {

        if (INSTANCE == null) {
            synchronized (LoginDataBase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                                    context, LoginDataBase.class, "LOGIN_DATABASE").allowMainThreadQueries()
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

}