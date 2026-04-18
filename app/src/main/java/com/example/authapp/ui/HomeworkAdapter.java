package com.example.authapp.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import com.example.authapp.R;
import com.example.authapp.model.Homework;
import com.example.authapp.model.Subject;
import com.example.authapp.viewmodel.DiaryViewModel;

import java.util.HashMap;
import java.util.Map;

public class HomeworkAdapter extends ListAdapter<Homework, HomeworkAdapter.VH> {
    private final Context ctx;
    private final DiaryViewModel viewModel;
    private Map<Integer, String> subjectNames = new HashMap<>();

    public HomeworkAdapter(Context ctx, DiaryViewModel viewModel) {
        super(new DiffUtil.ItemCallback<Homework>() {
            @Override
            public boolean areItemsTheSame(@NonNull Homework o, @NonNull Homework n) {
                return o.id == n.id;
            }
            @Override
            public boolean areContentsTheSame(@NonNull Homework o, @NonNull Homework n) {
                return o.equals(n);
            }
        });
        this.ctx = ctx;
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
        return new VH(LayoutInflater.from(ctx)
                .inflate(R.layout.item_homework, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        Homework hw = getItem(position);


        String subjectName = subjectNames.get(hw.subjectId);
        if (subjectName != null) {
            holder.desc.setText(subjectName + ": " + hw.description);
        } else {
            holder.desc.setText(hw.description);
        }

        holder.deadline.setText("Сдать до: " + hw.deadline);
        holder.cb.setChecked(hw.isDone);
        holder.cb.setOnCheckedChangeListener(null);
        holder.cb.setOnCheckedChangeListener((button, checked) -> viewModel.toggleHomework(hw));
    }

    static class VH extends RecyclerView.ViewHolder {
        CheckBox cb;
        TextView desc, deadline;
        VH(View v) {
            super(v);
            cb = v.findViewById(R.id.cbDone);
            desc = v.findViewById(R.id.tvDesc);
            deadline = v.findViewById(R.id.tvDeadline);
        }
    }
}