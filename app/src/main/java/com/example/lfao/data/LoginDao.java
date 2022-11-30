package com.example.lfao.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface LoginDao {

    @Insert
    void insertDetails(LoginTable data);

    @Query("select * from LoginDetails")
    default LiveData<List<LoginTable>> getDetails() {
        return null;
    }

    @Query("delete from LoginDetails")
    void deleteAllData();

}
