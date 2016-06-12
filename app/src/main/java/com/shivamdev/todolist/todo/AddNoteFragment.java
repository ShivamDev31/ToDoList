package com.shivamdev.todolist.todo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.shivamdev.todolist.R;
import com.shivamdev.todolist.database.DbHelper;
import com.shivamdev.todolist.database.NoteData;
import com.shivamdev.todolist.main.LogToast;
import com.shivamdev.todolist.main.MainActivity;

/**
 * Created by shivamchopra on 06/06/16.
 */
public class AddNoteFragment extends Fragment implements View.OnClickListener {
    private static final String EDIT_KEY = "edit";

    private EditText etNoteTitle;
    private EditText etNoteDesc;
    private Button bAddNote;
    private NoteData noteData;

    public static AddNoteFragment newInstance(NoteData noteData) {
        AddNoteFragment fragment = new AddNoteFragment();
        Bundle args = new Bundle();
        args.putSerializable(EDIT_KEY, noteData);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_notes, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        etNoteTitle = (EditText) view.findViewById(R.id.et_title);
        etNoteDesc = (EditText) view.findViewById(R.id.et_note_desc);
        bAddNote = (Button) view.findViewById(R.id.b_add);
        FloatingActionButton fabDeleteNote = (FloatingActionButton) view.findViewById(R.id.fab_delete_note);

        noteData = (NoteData) getArguments().getSerializable(EDIT_KEY);

        bAddNote.setOnClickListener(this);
        fabDeleteNote.setOnClickListener(this);

        if (noteData != null) {
            fabDeleteNote.setVisibility(View.VISIBLE);
            MainActivity activity = (MainActivity) getActivity();
            activity.initializeToolbar(true, noteData.title);
            etNoteTitle.setText(noteData.title);
            etNoteDesc.setText(noteData.desc);
            bAddNote.setText(getString(R.string.edit));
        } else {
            fabDeleteNote.setVisibility(View.GONE);
            bAddNote.setText(getString(R.string.add));
        }
    }

    private void addNoteToDb() {
        String noteTitle = etNoteTitle.getText().toString();
        String noteDesc = etNoteDesc.getText().toString();

        if (TextUtils.isEmpty(noteTitle) || TextUtils.isEmpty(noteDesc)) {
            LogToast.toast(getActivity(), getString(R.string.empty_title_note));
            return;
        } else {
            DbHelper dbHelper = DbHelper.getInstance(getActivity());
            NoteData note = new NoteData();
            note.title = noteTitle;
            note.desc = noteDesc;
            if (noteData == null) {
                dbHelper.addNote(note);
                LogToast.toast(getActivity(), getString(R.string.note_success));
            } else {
                note.id = noteData.id;
                dbHelper.editNote(note);
                LogToast.toast(getActivity(), getString(R.string.note_edited));
            }
            getActivity().getSupportFragmentManager().popBackStack();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.b_add:
                addNoteToDb();
                break;
            case R.id.fab_delete_note:
                deleteNote();
                break;
        }
    }

    private void deleteNote() {
        DbHelper dbHelper = DbHelper.getInstance(getActivity());
        dbHelper.deleteNote(noteData);
        LogToast.toast(getActivity(), "Note deleted Successfully");
        getActivity().getSupportFragmentManager().popBackStack();
    }
}
