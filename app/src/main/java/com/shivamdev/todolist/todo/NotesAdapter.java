package com.shivamdev.todolist.todo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shivamdev.todolist.R;
import com.shivamdev.todolist.database.NoteData;
import com.shivamdev.todolist.main.MainActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shivamchopra on 07/06/16.
 */
public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NotesHolder> {

    private static final String TAG = NotesAdapter.class.getSimpleName();

    private Context mContext;
    private List<NoteData> notesList;

    public NotesAdapter(Context context) {
        this.mContext = context;
        notesList = new ArrayList<>();
    }

    @Override
    public NotesHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.note_item, parent, false);
        return new NotesHolder(view);
    }

    @Override
    public void onBindViewHolder(NotesHolder holder, int position) {
        holder.tvTitle.setText(notesList.get(position).title);
        holder.tvDesc.setText(notesList.get(position).desc);
    }

    public void refreshList(List<NoteData> list) {
        notesList.clear();
        notesList.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return notesList.size();
    }

    public class NotesHolder extends RecyclerView.ViewHolder {
        private TextView tvTitle;
        private TextView tvDesc;

        public NotesHolder(View view) {
            super(view);
            tvTitle = (TextView) view.findViewById(R.id.tv_title);
            tvDesc = (TextView) view.findViewById(R.id.tv_desc);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mContext instanceof MainActivity) {
                        MainActivity activity = (MainActivity) mContext;
                        activity.addEditFragment(notesList.get(getLayoutPosition()));
                    }
                }
            });
        }
    }
}
