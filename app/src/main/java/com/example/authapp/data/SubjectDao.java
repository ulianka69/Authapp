package com.example.authapp.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import com.example.authapp.model.Subject;
import java.util.List;

@Dao
public interface SubjectDao {
    @Insert
    void insert(Subject subject);

    @Query("SELECT * FROM subjects")
    LiveData<List<Subject>> getAll();

    @Query("SELECT name FROM subjects WHERE id = :id")
    String getSubjectNameById(int id);
}