package com.shivamdev.todolist.database;

import java.io.Serializable;

/**
 * Created by shivamchopra on 11/06/16.
 */

public class NoteData implements Serializable {
    public int id;
    public String title;
    public String desc;
}