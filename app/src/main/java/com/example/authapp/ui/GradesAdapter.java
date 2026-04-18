package com.example.authapp.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import com.example.authapp.R;
import com.example.authapp.model.Grade;
import com.example.authapp.model.Subject;
import com.example.authapp.viewmodel.DiaryViewModel;

import java.util.HashMap;
import java.util.Map;

public class GradesAdapter extends ListAdapter<Grade, GradesAdapter.VH> {
    private final DiaryViewModel viewModel;
    private Map<Integer, String> subjectNames = new HashMap<>();

    public GradesAdapter(DiaryViewModel viewModel) {
        super(new DiffUtil.ItemCallback<Grade>() {
            @Override
            public boolean areItemsTheSame(@NonNull Grade o, @NonNull Grade n) {
                return o.id == n.id;
            }
            @Override
            public boolean areContentsTheSame(@NonNull Grade o, @NonNull Grade n) {
                return o.equals(n);
            }
        });
        this.viewModel = viewModel;


        viewModel.getSubjects().observeForever(subjects -> {
            subjectNames.clear();
            for (Subject subject : subjects) {
                subjectNames.put(subject.id, subject.name);
            }
        });
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VH(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_grade, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        Grade g = getItem(position);


        String subjectName = subjectNames.get(g.subjectId);
        if (subjectName != null) {
            holder.subject.setText(subjectName);
        } else {
            holder.subject.setText("Загрузка...");
        }

        holder.value.setText(String.valueOf(g.value));
        holder.date.setText(g.date);
        holder.comment.setText(g.comment);
    }

    static class VH extends RecyclerView.ViewHolder {
        TextView subject, value, date, comment;
        VH(View v) {
            super(v);
            subject = v.findViewById(R.id.tvSubject);
            value = v.findViewById(R.id.tvValue);
            date = v.findViewById(R.id.tvDate);
            comment = v.findViewById(R.id.tvComment);
        }
    }
}