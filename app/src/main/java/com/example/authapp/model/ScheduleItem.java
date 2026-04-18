package com.example.authapp.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.ForeignKey;

@Entity(tableName = "schedule",
        foreignKeys = @ForeignKey(entity = Subject.class, parentColumns = "id", childColumns = "subjectId", onDelete = ForeignKey.CASCADE))
public class ScheduleItem {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public int subjectId;
    public int dayOfWeek;
    public String time;
    public String room;

    public ScheduleItem(int subjectId, int dayOfWeek, String time, String room) {
        this.subjectId = subjectId;
        this.dayOfWeek = dayOfWeek;
        this.time = time;
        this.room = room;
    }
}