package com.example.authapp.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import com.example.authapp.model.ScheduleItem;
import java.util.List;

@Dao
public interface ScheduleDao {
    @Insert
    void insert(ScheduleItem item);

    @Query("SELECT * FROM schedule WHERE dayOfWeek = :day ORDER BY time")
    LiveData<List<ScheduleItem>> getByDay(int day);
}