package com.example.authapp.viewmodel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.example.authapp.model.*;
import com.example.authapp.repository.DiaryRepository;
import java.util.List;

public class DiaryViewModel extends AndroidViewModel {
    private final DiaryRepository repo;

    public DiaryViewModel(Application app) {
        super(app);
        repo = new DiaryRepository(app);
        repo.seedData();
    }

    public LiveData<List<Subject>> getSubjects() { return repo.getAllSubjects(); }
    public LiveData<List<ScheduleItem>> getSchedule(int day) { return repo.getScheduleByDay(day); }
    public LiveData<List<Grade>> getGrades() { return repo.getAllGrades(); }
    public LiveData<List<Homework>> getHomework() { return repo.getAllHomework(); }

    public void addSubject(Subject s) { repo.insertSubject(s); }
    public void addSchedule(ScheduleItem item) { repo.insertSchedule(item); }
    public void addGrade(Grade g) { repo.insertGrade(g); }
    public void addHomework(Homework hw) { repo.insertHomework(hw); }
    public void toggleHomework(Homework hw) {
        hw.isDone = !hw.isDone;
        repo.updateHomework(hw);
    }
}