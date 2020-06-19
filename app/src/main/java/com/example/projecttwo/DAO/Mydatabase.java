package com.example.projecttwo.DAO;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.projecttwo.Interface.DaoAccess;

@Database(entities = {Todo.class}, version = 1, exportSchema = false)
public abstract class Mydatabase extends RoomDatabase {
    public static final String DB_NAME = "app_db";
    public static final String TABLE_NAME_TODO = "todo";

    public abstract DaoAccess daoAccess();

}