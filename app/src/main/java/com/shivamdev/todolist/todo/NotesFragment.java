package com.shivamdev.todolist.todo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shivamdev.todolist.R;
import com.shivamdev.todolist.database.DbHelper;
import com.shivamdev.todolist.database.NoteData;

import java.util.List;

/**
 * Created by shivamchopra on 06/06/16.
 */
public class NotesFragment extends Fragment {
    public static final String ADD_TAG = "add_tag";

    private RecyclerView rvList;
    private TextView tvError;
    private NotesAdapter adapter;

    public static NotesFragment newInstance() {
        NotesFragment fragment = new NotesFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notes, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvList = (RecyclerView) view.findViewById(R.id.rv_notes_list);
        tvError = (TextView) view.findViewById(R.id.tv_no_notes);
        adapter = new NotesAdapter(getActivity());
        rvList.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvList.setAdapter(adapter);
        FloatingActionButton bAddNote = (FloatingActionButton) view.findViewById(R.id.fab_add_note);
        bAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addAddNotesFragment();
            }
        });

        showNotesList();
    }

    private void addAddNotesFragment() {
        AddNoteFragment notesFragment = AddNoteFragment.newInstance(null);
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.ll_fragment_layout, notesFragment, ADD_TAG)
                .addToBackStack(null).commit();
    }

    private void showNotesList() {
        DbHelper dbHelper = DbHelper.getInstance(getActivity());
        List<NoteData> notesData = dbHelper.getNotes();
        if (notesData.size() == 0) {
            showError(true);
        } else {
            showError(false);
            adapter.refreshList(notesData);
        }
    }

    private void showError(boolean isVisible) {
        if (isVisible) {
            rvList.setVisibility(View.GONE);
            tvError.setVisibility(View.VISIBLE);
        } else {
            tvError.setVisibility(View.GONE);
            rvList.setVisibility(View.VISIBLE);
        }
    }
}
