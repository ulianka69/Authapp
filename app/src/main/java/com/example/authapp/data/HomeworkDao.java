package com.example.authapp.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import com.example.authapp.model.Homework;
import java.util.List;

@Dao
public interface HomeworkDao {
    @Insert
    void insert(Homework hw);

    @Update
    void update(Homework hw);

    @Query("SELECT * FROM homework ORDER BY deadline")
    LiveData<List<Homework>> getAll();
}