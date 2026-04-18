package com.example.authapp.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.ForeignKey;

@Entity(tableName = "grades",
        foreignKeys = @ForeignKey(entity = Subject.class, parentColumns = "id", childColumns = "subjectId", onDelete = ForeignKey.CASCADE))
public class Grade {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public int subjectId;
    public int value;
    public String date;
    public String comment;

    public Grade(int subjectId, int value, String date, String comment) {
        this.subjectId = subjectId;
        this.value = value;
        this.date = date;
        this.comment = comment;
    }
}