package com.example.authapp.data;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;


import com.example.authapp.model.User;
import com.example.authapp.model.Subject;
import com.example.authapp.model.ScheduleItem;
import com.example.authapp.model.Grade;
import com.example.authapp.model.Homework;

@Database(
        entities = {
                User.class,
                Subject.class,
                ScheduleItem.class,
                Grade.class,
                Homework.class
        },
        version = 2,
        exportSchema = false
)
public abstract class AppDatabase extends RoomDatabase {

    public abstract UserDao userDao();


    public abstract SubjectDao subjectDao();
    public abstract ScheduleDao scheduleDao();
    public abstract GradeDao gradeDao();
    public abstract HomeworkDao homeworkDao();

    private static volatile AppDatabase INSTANCE;

    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDatabase.class, "diary_db")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}