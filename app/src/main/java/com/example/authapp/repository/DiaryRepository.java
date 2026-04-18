package com.example.authapp.repository;

import android.app.Application;
import androidx.lifecycle.LiveData;
import com.example.authapp.data.*;
import com.example.authapp.model.*;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class DiaryRepository {
    private final SubjectDao subjectDao;
    private final ScheduleDao scheduleDao;
    private final GradeDao gradeDao;
    private final HomeworkDao homeworkDao;
    private final Executor executor = Executors.newSingleThreadExecutor();
    private boolean dataSeeded = false;

    public DiaryRepository(Application app) {
        AppDatabase db = AppDatabase.getInstance(app);
        subjectDao = db.subjectDao();
        scheduleDao = db.scheduleDao();
        gradeDao = db.gradeDao();
        homeworkDao = db.homeworkDao();
    }

    public LiveData<List<Subject>> getAllSubjects() { return subjectDao.getAll(); }
    public LiveData<List<ScheduleItem>> getScheduleByDay(int day) { return scheduleDao.getByDay(day); }
    public LiveData<List<Grade>> getAllGrades() { return gradeDao.getAll(); }
    public LiveData<List<Homework>> getAllHomework() { return homeworkDao.getAll(); }

    public void insertSubject(Subject s) { executor.execute(() -> subjectDao.insert(s)); }
    public void insertSchedule(ScheduleItem item) { executor.execute(() -> scheduleDao.insert(item)); }
    public void insertGrade(Grade g) { executor.execute(() -> gradeDao.insert(g)); }
    public void insertHomework(Homework hw) { executor.execute(() -> homeworkDao.insert(hw)); }
    public void updateHomework(Homework hw) { executor.execute(() -> homeworkDao.update(hw)); }


    public void seedData() {
        if (dataSeeded) return;

        executor.execute(() -> {
            try {
                CountDownLatch latch = new CountDownLatch(1);
                final boolean[] hasData = {false};

                executor.execute(() -> {
                    try {
                        List<Subject> subjects = subjectDao.getAll().getValue();
                        hasData[0] = (subjects != null && !subjects.isEmpty());
                    } catch (Exception e) {
                        hasData[0] = false;
                    }
                    latch.countDown();
                });

                latch.await(2, TimeUnit.SECONDS);

                if (hasData[0]) {
                    dataSeeded = true;
                    return;
                }


                Subject math = new Subject("Математика", "Иванова А.П.");
                Subject physics = new Subject("Физика", "Петров С.В.");
                Subject history = new Subject("История России", "Сидорова М.К.");
                Subject russian = new Subject("Русский язык", "Козлова Е.Д.");
                Subject english = new Subject("Английский язык", "Smith J.");

                subjectDao.insert(math);
                subjectDao.insert(physics);
                subjectDao.insert(history);
                subjectDao.insert(russian);
                subjectDao.insert(english);


                scheduleDao.insert(new ScheduleItem(1, 1, "08:30", "305")); // Математика
                scheduleDao.insert(new ScheduleItem(4, 1, "09:20", "210")); // Русский язык
                scheduleDao.insert(new ScheduleItem(2, 1, "10:10", "412")); // Физика
                scheduleDao.insert(new ScheduleItem(3, 1, "11:10", "301")); // История
                scheduleDao.insert(new ScheduleItem(5, 1, "12:00", "215")); // Английский


                scheduleDao.insert(new ScheduleItem(3, 2, "08:30", "301")); // История
                scheduleDao.insert(new ScheduleItem(5, 2, "09:20", "215")); // Английский
                scheduleDao.insert(new ScheduleItem(1, 2, "10:10", "305")); // Математика
                scheduleDao.insert(new ScheduleItem(2, 2, "11:10", "412")); // Физика
                scheduleDao.insert(new ScheduleItem(4, 2, "12:00", "210")); // Русский язык


                scheduleDao.insert(new ScheduleItem(2, 3, "08:30", "412"));
                scheduleDao.insert(new ScheduleItem(1, 3, "09:20", "305"));
                scheduleDao.insert(new ScheduleItem(4, 3, "10:10", "210"));
                scheduleDao.insert(new ScheduleItem(3, 3, "11:10", "301"));
                scheduleDao.insert(new ScheduleItem(5, 3, "12:00", "215"));


                scheduleDao.insert(new ScheduleItem(5, 4, "08:30", "215"));
                scheduleDao.insert(new ScheduleItem(3, 4, "09:20", "301"));
                scheduleDao.insert(new ScheduleItem(1, 4, "10:10", "305"));
                scheduleDao.insert(new ScheduleItem(4, 4, "11:10", "210"));
                scheduleDao.insert(new ScheduleItem(2, 4, "12:00", "412"));


                scheduleDao.insert(new ScheduleItem(1, 5, "08:30", "305"));
                scheduleDao.insert(new ScheduleItem(2, 5, "09:20", "412"));
                scheduleDao.insert(new ScheduleItem(3, 5, "10:10", "301"));
                scheduleDao.insert(new ScheduleItem(5, 5, "11:10", "215"));
                scheduleDao.insert(new ScheduleItem(4, 5, "12:00", "210"));


                gradeDao.insert(new Grade(1, 5, "2024-10-01", "Контрольная работа"));
                gradeDao.insert(new Grade(1, 4, "2024-10-08", "Домашнее задание"));
                gradeDao.insert(new Grade(1, 5, "2024-10-15", "Самостоятельная"));
                gradeDao.insert(new Grade(1, 3, "2024-10-22", "Тест"));


                gradeDao.insert(new Grade(2, 4, "2024-10-03", "Лабораторная №1"));
                gradeDao.insert(new Grade(2, 5, "2024-10-10", "Доклад"));
                gradeDao.insert(new Grade(2, 4, "2024-10-17", "Практическая"));
                gradeDao.insert(new Grade(2, 3, "2024-10-24", "Тест"));


                gradeDao.insert(new Grade(3, 5, "2024-10-02", "Сочинение"));
                gradeDao.insert(new Grade(3, 4, "2024-10-09", "Тест"));
                gradeDao.insert(new Grade(3, 5, "2024-10-16", "Проект"));
                gradeDao.insert(new Grade(3, 5, "2024-10-23", "Презентация"));


                gradeDao.insert(new Grade(4, 4, "2024-10-04", "Диктант"));
                gradeDao.insert(new Grade(4, 5, "2024-10-11", "Изложение"));
                gradeDao.insert(new Grade(4, 3, "2024-10-18", "Грамматика"));
                gradeDao.insert(new Grade(4, 4, "2024-10-25", "Сочинение"));


                gradeDao.insert(new Grade(5, 5, "2024-10-05", "Speaking"));
                gradeDao.insert(new Grade(5, 4, "2024-10-12", "Grammar test"));
                gradeDao.insert(new Grade(5, 5, "2024-10-19", "Vocabulary"));
                gradeDao.insert(new Grade(5, 5, "2024-10-26", "Essay"));


                homeworkDao.insert(new Homework(1, "№124-130 стр.45, задача на проценты", "2024-10-10", false));
                homeworkDao.insert(new Homework(1, "Параграф 12, выучить формулы", "2024-10-17", false));
                homeworkDao.insert(new Homework(1, "Контрольная работа подготовка", "2024-10-24", true));


                homeworkDao.insert(new Homework(2, "Параграф 12, вопросы 1-5 письменно", "2024-10-11", false));
                homeworkDao.insert(new Homework(2, "Лабораторная №3, оформить отчет", "2024-10-18", false));
                homeworkDao.insert(new Homework(2, "Решить задачи из учебника", "2024-10-25", true));


                homeworkDao.insert(new Homework(3, "Прочитать главу 5, пересказ", "2024-10-12", false));
                homeworkDao.insert(new Homework(3, "Подготовить презентацию о Петре I", "2024-10-19", false));
                homeworkDao.insert(new Homework(3, "Тест по датам", "2024-10-26", true));


                homeworkDao.insert(new Homework(4, "Упражнение 234, разбор предложений", "2024-10-10", false));
                homeworkDao.insert(new Homework(4, "Сочинение-рассуждение", "2024-10-17", false));
                homeworkDao.insert(new Homework(4, "Выучить правила", "2024-10-24", true));


                homeworkDao.insert(new Homework(5, "Выучить 20 новых слов", "2024-10-11", false));
                homeworkDao.insert(new Homework(5, "Написать эссе 'My Family'", "2024-10-18", false));
                homeworkDao.insert(new Homework(5, "Grammar exercises p.45", "2024-10-25", true));

                dataSeeded = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}