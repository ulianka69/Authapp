package com.example.authapp.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.authapp.R;
import com.example.authapp.viewmodel.DiaryViewModel;

public class GradesFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_grades, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        RecyclerView rv = view.findViewById(R.id.rvGrades);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));

        DiaryViewModel vm = new ViewModelProvider(this).get(DiaryViewModel.class);
        GradesAdapter adapter = new GradesAdapter(vm); // <-- Передаем ViewModel
        rv.setAdapter(adapter);

        vm.getGrades().observe(getViewLifecycleOwner(), adapter::submitList);
    }
}