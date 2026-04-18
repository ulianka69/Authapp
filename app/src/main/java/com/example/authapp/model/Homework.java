package com.example.authapp.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.ForeignKey;

@Entity(tableName = "homework",
        foreignKeys = @ForeignKey(entity = Subject.class, parentColumns = "id", childColumns = "subjectId", onDelete = ForeignKey.CASCADE))
public class Homework {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public int subjectId;
    public String description;
    public String deadline;
    public boolean isDone;

    public Homework(int subjectId, String description, String deadline, boolean isDone) {
        this.subjectId = subjectId;
        this.description = description;
        this.deadline = deadline;
        this.isDone = isDone;
    }
}