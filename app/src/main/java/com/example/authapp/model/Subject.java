package com.example.authapp.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "subjects")
public class Subject {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String name;
    public String teacher;

    public Subject(String name, String teacher) {
        this.name = name;
        this.teacher = teacher;
    }
}