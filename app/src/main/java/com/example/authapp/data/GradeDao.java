package com.example.authapp.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import com.example.authapp.model.Grade;
import java.util.List;

@Dao
public interface GradeDao {
    @Insert
    void insert(Grade grade);

    @Query("SELECT * FROM grades ORDER BY date DESC")
    LiveData<List<Grade>> getAll();
}