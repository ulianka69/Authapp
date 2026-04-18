package com.example.authapp.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import com.example.authapp.R;
import com.example.authapp.model.ScheduleItem;
import com.example.authapp.model.Subject;
import com.example.authapp.viewmodel.DiaryViewModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScheduleAdapter extends ListAdapter<ScheduleItem, ScheduleAdapter.VH> {
    private final DiaryViewModel viewModel;
    private Map<Integer, String> subjectNames = new HashMap<>();

    public ScheduleAdapter(DiaryViewModel viewModel) {
        super(new DiffUtil.ItemCallback<ScheduleItem>() {
            @Override
            public boolean areItemsTheSame(@NonNull ScheduleItem old, @NonNull ScheduleItem n) {
                return old.id == n.id;
            }
            @Override
            public boolean areContentsTheSame(@NonNull ScheduleItem old, @NonNull ScheduleItem n) {
                return old.equals(n);
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
                .inflate(R.layout.item_schedule, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        ScheduleItem item = getItem(position);
        holder.time.setText(item.time);
        holder.room.setText("Каб. " + item.room);


        String subjectName = subjectNames.get(item.subjectId);
        if (subjectName != null) {
            holder.subject.setText(subjectName);
        } else {
            holder.subject.setText("Загрузка...");
        }
    }

    static class VH extends RecyclerView.ViewHolder {
        TextView time, subject, room;
        VH(View v) {
            super(v);
            time = v.findViewById(R.id.tvTime);
            subject = v.findViewById(R.id.tvSubject);
            room = v.findViewById(R.id.tvRoom);
        }
    }
}