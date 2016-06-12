package com.shivamdev.todolist.main;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.shivamdev.todolist.R;
import com.shivamdev.todolist.database.NoteData;
import com.shivamdev.todolist.todo.AddNoteFragment;
import com.shivamdev.todolist.todo.NotesFragment;

public class MainActivity extends BaseActivity {
    public static final String NOTES_TAG = "notes_tag";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        initializeToolbar(false, getString(R.string.app_name));
        addNotesFragment();
    }

    private void addNotesFragment() {
        NotesFragment notesFragment = NotesFragment.newInstance();
        getSupportFragmentManager().beginTransaction().add(R.id.ll_fragment_layout, notesFragment, NOTES_TAG).commit();
    }

    public void addEditFragment(NoteData note) {
        AddNoteFragment noteFragment = AddNoteFragment.newInstance(note);
        getSupportFragmentManager().beginTransaction().replace(R.id.ll_fragment_layout, noteFragment, NOTES_TAG)
                .addToBackStack(null).commit();
    }
}
